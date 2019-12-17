package com.sfelaco.rsocket.pojos;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("ztlaccess")

public class ZTLAccess {
	
	@Id
	private Long id;
	private Date date;
	@Column("gateId")
	private Integer gateId;
	private Integer exempt;
	private Integer motorbike;
	private Integer resident;
	@Column("vehicleService")
	private String vehicleService;
	@Column("euroCategory")
	private String euroCategory;
	@Column("supplyType")
	private String supplyType;
	@Column("vehicleCategory")
	private String vehicleCategory;
	@Column("areaCClass")
	private String areaCClass;
	private String fap;
	@Column("areaC")
	private String areaC;
	@Column("numAccecces")
	private Integer numAccecces;
	
	
	
	
	
}
