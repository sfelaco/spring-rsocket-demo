package com.sfelaco.rsocket.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumAccessesResponse {

	private Long accesses;
	private String area;
	
}
