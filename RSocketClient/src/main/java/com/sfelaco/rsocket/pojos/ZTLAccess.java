package com.sfelaco.rsocket.pojos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZTLAccess {

	private Date date;
	private String id;
	private Integer exempt;
	private Integer motorbike;
	private Integer resident;
	private String vehicleService;
	private String euroCategory;
	private String supplyType;
	private String vehicleCategory;
	private String areaCClass;
	private String fap;
	private String areaC;
	private Integer numAccecces;
	
	
	
	
	
}
