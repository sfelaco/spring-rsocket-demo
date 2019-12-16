package com.sfelaco.rsocket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sfelaco.rsocket.pojos.ZTLAcces;
import com.sfelaco.rsocket.repositories.ZTLAccessRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@SpringBootTest
@Slf4j
class LoadCSV {

	@Autowired
	private ZTLAccessRepository repo;
	

	@Test
	public void loadCVS() {
		try {
			Resource csvFile = new ClassPathResource("ingressi_areac_2019_10.csv");
			CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(csvFile.getInputStream())).withSkipLines(1)
					.build();

			
			Flux<ZTLAcces> fluxAccess = Flux.create(emitter -> {
				String[] nextRecord = null;
	            try {
					while ((nextRecord = csvReader.readNext()) != null) {
						emitter.next(toZTLAccess(nextRecord));
					}
					emitter.complete();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
					emitter.error(e);
				}

			},FluxSink.OverflowStrategy.BUFFER);
			AtomicInteger i = new AtomicInteger();
			i.set(0);
			ConnectableFlux<ZTLAcces> hotFluxAccess = fluxAccess.publish();
			repo.saveAll(fluxAccess)
				.doOnComplete(() -> {log.info("######### COMPLETED ######");}).subscribe(a -> log.info(a.toString()));
			//hotFluxAccess.subscribe(a-> log.info(a.toString()));
			//hotFluxAccess.connect();

			while (i.get() == 0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	
	
	
	@Test
	public void loadCVS2() {
		try {
			Resource csvFile = new ClassPathResource("ingressi_areac_2019_10.csv");
			CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(csvFile.getInputStream())).withSkipLines(1)
					.build();

			String[] nextRecord = null;
			List<ZTLAcces> emitter = new ArrayList<>();
			while ((nextRecord = csvReader.readNext()) != null) {
				emitter.add(toZTLAccess(nextRecord));
			}
			log.info("Loading accesses");
			AtomicInteger i = new AtomicInteger();
			i.set(0);
			repo.saveAll(emitter)
				.doOnComplete(() -> {log.info("######### COMPLETED ######");}).subscribe(a -> log.info(a.toString()));
			//hotFluxAccess.subscribe(a-> log.info(a.toString()));
			//hotFluxAccess.connect();

			while (i.get() == 0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	
	

	private ZTLAcces toZTLAccess(String[] nextRecord) {
		ZTLAcces access = new ZTLAcces();
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
