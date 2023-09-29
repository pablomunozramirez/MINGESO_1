package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudiantesService {
    @Autowired
    EstudiantesRepository estudiantesRepository;
}
