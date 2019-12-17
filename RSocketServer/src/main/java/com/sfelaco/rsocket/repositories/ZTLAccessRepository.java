package com.sfelaco.rsocket.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.sfelaco.rsocket.pojos.ZTLAccess;

public interface ZTLAccessRepository extends R2dbcRepository<ZTLAccess, String> {

}