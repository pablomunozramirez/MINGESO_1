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
        Optional<EstudiantesEntity> existingEstudiante = estudiantesRepository.findById(estudianteRequest.getRut());

        if (existingEstudiante.isPresent()) {
            throw new EntityExistsException("Ya existe un estudiante con el mismo rut: " + estudianteRequest.getRut());
        }
        EstudiantesEntity estudiante = convertirAEntidad(estudianteRequest);
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

    public int existeEstudiante(String rut) {
        Optional<EstudiantesEntity> estudiante = estudiantesRepository.findById(rut);
        return estudiante.isPresent() ? 1 : 0;
    }

    public int obtenerTipo(String rut){
        Optional <EstudiantesEntity> estudianteOptional = estudiantesRepository.findById(rut);
        if (estudianteOptional.isEmpty()) {
            throw new EntityExistsException("Ya existe un estudiante con el mismo rut: " + rut);
        }
        EstudiantesEntity estudiante = estudianteOptional.get();
        String tipo = estudiante.getTipo_colegio();
        if (tipo.equals("1")){
            return 1;
        }else if (tipo.equals("2")){
            return 2;
        }else{
            return 3;
        }

    }

    public double calcularArancel(EstudiantesEntity estudiante) {
        double arancel = 1500000;
        if (estudiante.getTipo_colegio().equals("1")){
            arancel = arancel * 0.8;
        } else if (estudiante.getTipo_colegio().equals("2")){
            arancel = arancel * 0.9;
        }
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

    public String buscarNombreByRut(String rut){
        Optional <EstudiantesEntity> estudianteOptional = estudiantesRepository.findById(rut);
        if (estudianteOptional.isEmpty()) {
            throw new EntityExistsException("Ya existe un estudiante con el mismo rut: " + rut);
        }
        EstudiantesEntity estudiante = estudianteOptional.get();
        return (estudiante.getNombre() + " " +estudiante.getApellidos());
    }
}
