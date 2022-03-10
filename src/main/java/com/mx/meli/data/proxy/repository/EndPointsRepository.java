package com.mx.meli.data.proxy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mx.meli.data.proxy.entity.EndPointEntity;

@Repository
public interface EndPointsRepository extends CrudRepository<EndPointEntity, Long>{

	
}
