package com.sfelaco.rsocket.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

import com.sfelaco.rsocket.pojos.NumAccessesResponse;
import com.sfelaco.rsocket.pojos.ZTLAccess;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ZTLAccessesAdapter {
	
	@Autowired
	private RSocketRequester rSocketRequester;
	
	
	public Mono<NumAccessesResponse> getNumAccesses(String area) {
		return rSocketRequester.route("accesses-number")
			.data(area).retrieveMono(NumAccessesResponse.class);
	}
	
	public Flux<ZTLAccess> getZTLAccessesStream(String area){
		return rSocketRequester.route("accesses-stream")
				.data(area).retrieveFlux(ZTLAccess.class);
	}
	

}
