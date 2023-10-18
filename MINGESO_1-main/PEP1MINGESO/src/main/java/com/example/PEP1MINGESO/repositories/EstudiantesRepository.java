package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudiantesRepository extends CrudRepository<EstudiantesEntity, String> {
    boolean existsByRut(String rut);
}
