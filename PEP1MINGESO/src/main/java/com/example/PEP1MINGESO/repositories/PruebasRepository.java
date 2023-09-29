package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.PruebasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebasRepository extends CrudRepository<PruebasEntity, Long> {

}
