package com.sfelaco.rsocket;


import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sfelaco.rsocket.adapters.ZTLAccessesAdapter;
import com.sfelaco.rsocket.pojos.NumAccessesResponse;
import com.sfelaco.rsocket.pojos.ZTLAccess;
import com.sfelaco.rsocket.pojos.ZTLAccessRequest;

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
	public Publisher<ZTLAccess> getAccessesStream(@PathVariable String area){
		log.info("######## GET ACCESSES STREAM ########");
		return ztlAdapter.getZTLAccessesStream(area);
	}
	

	
}
