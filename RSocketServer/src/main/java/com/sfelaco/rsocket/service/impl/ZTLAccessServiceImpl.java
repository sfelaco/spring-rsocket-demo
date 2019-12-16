package com.sfelaco.rsocket.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sfelaco.rsocket.pojos.ZTLAcces;
import com.sfelaco.rsocket.repositories.ZTLAccessRepository;
import com.sfelaco.rsocket.service.ZTLAccesesService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@Service
@Transactional
@Slf4j
public class ZTLAccessServiceImpl implements ZTLAccesesService {

	@Autowired
	private ZTLAccessRepository repo;

	@Override
	public Mono<Long> getNumAccesses(String area) {
		log.info("getAccessService");
		return repo.count();
	}

	@Override
	public Flux<ZTLAcces> getAccesses(String area) {
		/*
		 * return Flux.range(0, 100).delayElements(Duration.ofMillis(500)) .map(i ->
		 * getZTLAccessMock()).doOnNext(a -> log.info(a.getId()));
		 */
		return repo.findAll();

	}


	@Override
	public Flux<Map<String, Object>> addZtlAccess(ZTLAcces ztlAccess) {
		log.info("ADD ZTL");
		return repo.saveAll(Flux.just(ztlAccess));
	}

	@Override
	public Flux<Map<String, Object>> loadAll2() throws IOException {
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
				log.error(e.getMessage(), e);
				emitter.error(e);
			}

		}, FluxSink.OverflowStrategy.BUFFER);
		return repo.saveAll(fluxAccess);

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
