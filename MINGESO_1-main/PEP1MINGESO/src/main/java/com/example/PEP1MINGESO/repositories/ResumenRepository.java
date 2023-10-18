package com.example.PEP1MINGESO.repositories;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.ResumenEntity;
import org.springframework.data.repository.CrudRepository;

public interface ResumenRepository extends CrudRepository<ResumenEntity, Long> {
    boolean existsByRutEstudiante(String rutEstudiante);

    ResumenEntity findByRutEstudiante(String rutEstudiante);
}
