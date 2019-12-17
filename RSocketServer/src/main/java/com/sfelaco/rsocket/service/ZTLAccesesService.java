package com.sfelaco.rsocket.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sfelaco.rsocket.pojos.ZTLAccess;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ZTLAccesesService {

	Mono<Long> getNumAccesses(String area);

	Flux<ZTLAccess> getAccesses(String area);

	Flux<ZTLAccess> addZtlAccess(ZTLAccess ztlAccess);

	Flux<ZTLAccess> addZtlAccesses(List<ZTLAccess> ztlAccess);

	Flux<Map<String, Object>> addZTLAccesses2(List<ZTLAccess> ztlAccess);

}
