package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends CrudRepository <CuotaEntity, Long> {
    List<CuotaEntity> findByRutCuota(String rut);

    boolean existsByRutCuota(String rut);
}
