package com.sentinel.engis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sentinel.engis.dto.RedisSupplementalId;

@Repository
public interface SupplementalIdRedisRepository extends CrudRepository<RedisSupplementalId, Long>{
	RedisSupplementalId findByTpId(String tpId);
}
