package com.sfelaco.rsocket;

import java.util.Random;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

//@SpringBootTest
@Slf4j
class RSocketServerApplicationTests {

	@Test
	void contextLoads() throws InterruptedException {
		log.info("CIAO");
		Flux.range(0, 100).subscribeOn(Schedulers.newParallel("parallel-scheduler", 4))
			.map(i -> new Random().nextInt(11)).doOnNext(a -> log.info(a.toString()));
		log.info("#############");
		
		Thread.sleep(30000);
	}

}
