package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PruebasRepository extends CrudRepository<PruebasEntity, Long> {
    ArrayList<PruebasEntity> findByRutPrueba(String rut);

    long countByRutPrueba(String rut);


}
