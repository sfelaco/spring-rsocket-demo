package com.sfelaco.rsocket.repositories;

import java.util.Map;

import com.sfelaco.rsocket.pojos.ZTLAcces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ZTLAccessRepository {

	Flux<Map<String, Object>> saveAll(Flux<ZTLAcces> fluxAccess);
	
	
}
