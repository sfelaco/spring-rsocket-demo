package com.sfelaco.rsocket.controllers;


import java.time.Duration;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sfelaco.rsocket.adapters.ZTLAccessesAdapter;
import com.sfelaco.rsocket.pojos.NumAccessesResponse;
import com.sfelaco.rsocket.pojos.ZTLAccess;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ZTLAccessesClientController {

	@Autowired
	private ZTLAccessesAdapter ztlAdapter;
	
	@GetMapping("/num-accesses/{area}")
	public Mono<NumAccessesResponse> getNumAccesses(@PathVariable String area){
		return ztlAdapter.getNumAccesses(area);
	}
	
	@GetMapping("accesses-stream/{area}")
	public Publisher<ServerSentEvent<ZTLAccess>> getAccessesStream(@PathVariable String area){
		log.info("Get Ztl accesses stream");
		return ztlAdapter.getZTLAccessesStream(area).log().map(a -> 
			ServerSentEvent.<ZTLAccess> builder()
				.id(String.valueOf(a.getId()))
				.event("ztl-access").data(a).build()).delayElements(Duration.ofSeconds(2));
	}
	
	
}
