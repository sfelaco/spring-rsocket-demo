package com.sfelaco.rsocket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfelaco.rsocket.pojos.ZTLAccess;
import com.sfelaco.rsocket.repositories.ZTLAccessRepository;
import com.sfelaco.rsocket.service.ZTLAccesesService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
public class ZTLAccessServiceImpl implements ZTLAccesesService {

	@Autowired
	private ZTLAccessRepository repo;
	
	@Autowired
	private DatabaseClient databaseClient;

	@Override
	public Mono<Long> getNumAccesses(String area) {
		log.info("getAccessService");
		return repo.count();
	}

	@Override
	public Flux<ZTLAccess> getAccesses(String area) {
		return repo.findAll();
	}


	@Override
	public Flux<ZTLAccess> addZtlAccess(ZTLAccess ztlAccess) {
		return repo.saveAll(Flux.just(ztlAccess));
	}
	
	@Override
	public Flux<ZTLAccess> addZtlAccesses(List<ZTLAccess> ztlAccess) {
		return repo.saveAll(Flux.fromIterable(ztlAccess));
	}

	@Override
	public Flux<Map<String, Object>> addZTLAccesses2(List<ZTLAccess> ztlAccess) {
		return databaseClient.insert().into(ZTLAccess.class).using(Flux.fromIterable(ztlAccess)).fetch().all();
	}

}
