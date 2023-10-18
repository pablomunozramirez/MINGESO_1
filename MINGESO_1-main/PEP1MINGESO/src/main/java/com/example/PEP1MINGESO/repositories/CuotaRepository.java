package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CuotaRepository extends CrudRepository <CuotaEntity, Long> {
    ArrayList<CuotaEntity> findByRutCuota(String rut);

    long countByRutCuota(String rutCuota);

    boolean existsByRutCuota(String rut);
}
