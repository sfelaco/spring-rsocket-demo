package com.sfelaco.rsocket.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sfelaco.rsocket.pojos.ZTLAcces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ZTLAccesesService {

	Mono<Long> getNumAccesses(String area);

	Flux<ZTLAcces> getAccesses(String area);

	Flux<Map<String, Object>> addZtlAccess(ZTLAcces ztlAccess);

	Flux<Map<String, Object>> loadDB() throws IOException;

	Flux<Map<String, Object>> addZtlAccesses(List<ZTLAcces> ztlAccess);

}
