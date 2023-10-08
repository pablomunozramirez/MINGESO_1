package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuotaRepository extends CrudRepository <CuotaEntity, Long> {

}