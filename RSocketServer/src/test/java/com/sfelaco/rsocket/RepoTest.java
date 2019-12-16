package com.sfelaco.rsocket;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sfelaco.rsocket.pojos.ZTLAcces;
import com.sfelaco.rsocket.repositories.ZTLAccessRepository;
import com.sfelaco.rsocket.service.ZTLAccesesService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RepoTest {

	@Autowired
	private ZTLAccesesService ztlService;
	@Autowired
	private ZTLAccessRepository repo;
	
	@Test
	void contextLoads()  {
		try {
		ZTLAcces ztl = new ZTLAcces();
		ztl.setAreaC("C");
		ztl.setNumAccecces(10);

		ztlService.addZtlAccess(ztl).subscribe(z -> log.info("#### " + z));
		
		Thread.sleep(5000l);
		log.info("saved ztlAccess");
		} catch (Exception e ) {
			log.error(e.getMessage(),e);
			fail();
		}
	}
	
	@Test
	void testRepo() throws InterruptedException {
		ZTLAcces ztl = new ZTLAcces();
		ztl.setAreaC("C");
		ztl.setNumAccecces(10);

		repo.save(ztl).subscribe(z -> log.info("#### " + z));
		
		Thread.sleep(5000l);
	}
	
	@Test
	void testRepo2() {
		ZTLAcces ztl = new ZTLAcces();
		ztl.setAreaC("C");
		ztl.setNumAccecces(10);

		ztlService.getAccesses("C").subscribe(z -> log.info("#### " + z));
	}
	
	@Test
	void testDate() {
		String date = "2019/10/10 16:00:00+0002";
		DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ssZ");
		LocalDateTime aLD = LocalDateTime.parse(date,dTF);
		System.out.println("Date: " + aLD);
	}
	

}
