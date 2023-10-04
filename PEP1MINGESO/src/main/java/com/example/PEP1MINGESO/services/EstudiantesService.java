package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudiantesService {

    @Autowired
    EstudiantesRepository estudiantesRepository;

    public void crearEstudiante(EstudiantesEntity estudianteRequest) {
        // Realizar lógica de negocio
        EstudiantesEntity estudiante = convertirAEntidad(estudianteRequest);
        estudiantesRepository.save(estudiante);
    }

    private EstudiantesEntity convertirAEntidad(EstudiantesEntity estudianteRequest) {
        EstudiantesEntity estudiante = new EstudiantesEntity();
        estudiante.setRut(estudianteRequest.getRut());
        estudiante.setNombre(estudianteRequest.getNombre());
        estudiante.setApellidos(estudianteRequest.getApellidos());
        estudiante.setFecha_nacimiento(estudianteRequest.getFecha_nacimiento());
        estudiante.setTipo_colegio(estudianteRequest.getTipo_colegio());
        estudiante.setNombre_colegio(estudianteRequest.getNombre_colegio());
        estudiante.setAnio_egreso(estudianteRequest.getAnio_egreso());
        return estudiante;
    }
}
