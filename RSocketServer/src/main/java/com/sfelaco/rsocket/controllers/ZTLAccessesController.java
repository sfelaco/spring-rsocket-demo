package com.sfelaco.rsocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.sfelaco.rsocket.pojos.NumAccessesResponse;
import com.sfelaco.rsocket.pojos.ZTLAccess;
import com.sfelaco.rsocket.service.ZTLAccesesService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class ZTLAccessesController {
	
	@Autowired
	private ZTLAccesesService ztlAccessesService;
	
	@MessageMapping("accesses-number")
	public Mono<NumAccessesResponse> getNumAccesses(String area) {
		return ztlAccessesService.getNumAccesses(area).map(a -> new NumAccessesResponse(a, "C"));
		
	}
	
	@MessageMapping("accesses-stream")
	public Flux<ZTLAccess> getAccessesAsStream(String area){
		log.info("### Get Accesses Stream");
		return ztlAccessesService.getAccesses(area);
	}

 
}
