package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EstudiantesService {

    @Autowired
    EstudiantesRepository estudiantesRepository;

    public void crearEstudiante1(EstudiantesEntity estudianteRequest) {
        // Verificar si ya existe un estudiante con el mismo rut
        Optional<EstudiantesEntity> existingEstudiante = estudiantesRepository.findById(estudianteRequest.getRut());

        if (existingEstudiante.isPresent()) {
            // Manejar el caso cuando ya existe un estudiante con el mismo rut
            throw new EntityExistsException("Ya existe un estudiante con el mismo rut: " + estudianteRequest.getRut());
        }

        // Si no existe, asignar el rut y guardar el nuevo estudiante
        EstudiantesEntity estudiante = convertirAEntidad(estudianteRequest);
        estudiante.setArancel(calcularArancel(estudiante));
        estudiantesRepository.save(estudiante);
    }

    public EstudiantesEntity convertirAEntidad(EstudiantesEntity estudianteRequest) {
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

    public double calcularArancel(EstudiantesEntity estudiante) {
        double arancel = 1500000;
        //Aplica descuento por tipo de colegio
        if (estudiante.getTipo_colegio().equals("1")){
            arancel = arancel * 0.8;
        } else if (estudiante.getTipo_colegio().equals("2")){
            arancel = arancel * 0.9;
        }
        //Aplica descuento por años desde que egresó
        LocalDate current_date = LocalDate.now();
        int current_Year = current_date.getYear();
        int egreso = estudiante.getAnio_egreso();
        if(current_Year - egreso == 0){
            arancel = arancel * 0.85;
        } else if (current_Year - egreso < 3) {
            arancel = arancel * 0.92;
        } else if (current_Year - egreso < 5) {
            arancel = arancel*0.96;
        }
        return arancel;
    }
}
