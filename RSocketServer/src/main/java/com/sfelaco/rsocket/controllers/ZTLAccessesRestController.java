package com.sfelaco.rsocket.controllers;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sfelaco.rsocket.pojos.ZTLAcces;
import com.sfelaco.rsocket.service.ZTLAccesesService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

@RestController
@Slf4j
public class ZTLAccessesRestController {
	
	@Autowired
	private ZTLAccesesService ztlAccessesService;
	
	@GetMapping("add")
	public void add() {
		ZTLAcces ztl = new ZTLAcces();
		ztl.setAreaC("C");
		ztl.setNumAccecces(10);
		ztlAccessesService.addZtlAccess(ztl).subscribe(z -> log.info("######## " + z));
	}
	
	
	@GetMapping("load-db")
	public void loadDB() throws IOException {
		log.info("Loading cvs started...");
		final AtomicInteger i = new AtomicInteger(0);
		ztlAccessesService.loadDB()
		.subscribeOn(Schedulers.parallel())
		.subscribe(a -> {	
			log.info("Stored row num: " + i.incrementAndGet());
		});
	}
	
 
}
