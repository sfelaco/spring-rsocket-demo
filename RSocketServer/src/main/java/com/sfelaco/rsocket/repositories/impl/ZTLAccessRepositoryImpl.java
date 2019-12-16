package com.sfelaco.rsocket.repositories.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
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
		//return databaseClient.insert().into(ZTLAcces.class).using(fluxAccess).fetch().rowsUpdated();
		return databaseClient.insert().into(ZTLAcces.class).using(fluxAccess).fetch().all();
	}
	
}
