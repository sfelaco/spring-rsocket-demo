package com.sfelaco.rsocket.repositories.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.sql.Functions;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.StatementBuilder;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.core.sql.render.SqlRenderer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfelaco.rsocket.pojos.ZTLAcces;
import com.sfelaco.rsocket.repositories.ZTLAccessRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public class ZTLAccessRepositoryImpl implements ZTLAccessRepository {

	@Autowired
	private DatabaseClient databaseClient;

	@Override
	public Flux<Map<String, Object>> saveAll(Flux<ZTLAcces> fluxAccess) {
		return databaseClient.insert().into(ZTLAcces.class).using(fluxAccess).fetch().all();
	}

	@Override
	public Flux<ZTLAcces> findAll() {
		return databaseClient.select().from(ZTLAcces.class).as(ZTLAcces.class).all();
	}

	@Override
	public Mono<Long> count() {
		Table table = Table.create("ztlaccess");

		Select select = StatementBuilder 
				.select(Functions.count(table.column("id"))) 
				.from(table).build();

		return this.databaseClient.execute(SqlRenderer.toString(select)) 
				.map((r, md) -> r.get(0, Long.class)) 
				.first() //
				.defaultIfEmpty(0L);
	}

}
