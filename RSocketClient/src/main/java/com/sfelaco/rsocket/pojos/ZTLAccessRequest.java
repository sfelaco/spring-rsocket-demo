package com.sfelaco.rsocket.pojos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZTLAccessRequest {

	private List<ZTLAccess> ztlAccesses;
}
