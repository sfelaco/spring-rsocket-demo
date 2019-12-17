package com.sfelaco.rsocket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.DatabaseClient;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sfelaco.rsocket.pojos.ZTLAccess;
import com.sfelaco.rsocket.repositories.ZTLAccessRepository;
import com.sfelaco.rsocket.service.ZTLAccesesService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@SpringBootTest
@Slf4j
class RSocketServerApplicationTests {
	
	@Autowired
	private DatabaseClient databaseClient; 
	
	
	@Test
	void contextLoads() throws InterruptedException, IOException {
		log.info("###Loading csv file...####");
		Resource csvFile = new ClassPathResource("ingressi_areac_2019_10.csv");
		CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(csvFile.getInputStream())).withSkipLines(1)
				.build();

		Flux<ZTLAccess> fluxAccess = Flux.create(emitter -> {
			String[] nextRecord = null;
			try {
				while ((nextRecord = csvReader.readNext()) != null) {
					emitter.next(toZTLAccess(nextRecord));
				}
				emitter.complete();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				emitter.error(e);
			}

		}, FluxSink.OverflowStrategy.BUFFER);
		AtomicInteger i = new AtomicInteger(0);
		databaseClient.insert().into(ZTLAccess.class)
			.using(fluxAccess).fetch().all().doOnComplete(() -> {i.incrementAndGet();}).subscribe();
		
		while (i.get() == 0);
	}
	
	

	private ZTLAccess toZTLAccess(String[] nextRecord) {
		ZTLAccess access = new ZTLAccess();
		access.setDate(toDate(nextRecord[0]));
		access.setGateId(Integer.valueOf(nextRecord[1]));
		access.setExempt(Integer.valueOf(nextRecord[2]));
		access.setMotorbike(Integer.valueOf(nextRecord[3]));
		access.setResident(Integer.valueOf(nextRecord[4]));
		access.setVehicleService(nextRecord[5]);
		access.setEuroCategory(nextRecord[6]);
		access.setSupplyType(nextRecord[7]);
		access.setVehicleCategory(nextRecord[8]);
		access.setAreaCClass(nextRecord[9]);
		access.setFap(nextRecord[10]);
		access.setAreaC(nextRecord[11]);
		access.setNumAccecces(Integer.valueOf(nextRecord[12]));
		return access;
	}

	private Date toDate(String date) {
		DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ssZ");
		LocalDateTime aLD = LocalDateTime.parse(date, dTF);
		return Date.from(aLD.atZone(ZoneId.systemDefault()).toInstant());
	}
	
}
