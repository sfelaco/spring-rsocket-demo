package com.sfelaco.rsocket.controllers;

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
	
	
	@GetMapping("load")
	public void load() {
		log.info("Loading cvs started...");
		final AtomicInteger i = new AtomicInteger(0);
		ztlAccessesService.loadAll().doOnComplete(() -> {log.info("######### COMPLETED ######");})
			.subscribe(a -> {	
				log.info("Stored row num: " + i.incrementAndGet());
			});
	}
	
	@GetMapping("load2")
	public void load2() {
		log.info("Loading cvs started...");
		final AtomicInteger i = new AtomicInteger(0);
		ztlAccessesService.loadAll2().doOnComplete(() -> {log.info("######### COMPLETED ######");})
		.subscribeOn(Schedulers.parallel())
		.subscribe(a -> {	
			log.info("Stored row num: " + i.incrementAndGet());
		});
	}
	
 
}
