package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EstudiantesService {

    @Autowired
    EstudiantesRepository estudiantesRepository;

    public EstudiantesEntity crearEstudiante1(EstudiantesEntity estudianteRequest) {
        Optional<EstudiantesEntity> existingEstudiante = estudiantesRepository.findById(estudianteRequest.getRut());

        if (existingEstudiante.isPresent()) {
            throw new EntityExistsException("Ya existe un estudiante con el mismo rut: " + estudianteRequest.getRut());
        }
        estudiantesRepository.save(estudianteRequest);
        return estudianteRequest;
    }

    public int existeEstudiante(String rut) {
        Optional<EstudiantesEntity> estudiante = estudiantesRepository.findById(rut);
        return estudiante.isPresent() ? 1 : 0;
    }

    public int obtenerTipo(String rut){
        Optional <EstudiantesEntity> estudianteOptional = estudiantesRepository.findById(rut);
        if (estudianteOptional.isEmpty()) {
            throw new EntityExistsException("No existe el estudiante: " + rut);
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
    public ArrayList<EstudiantesEntity> obtenerestudiantes (){
        return (ArrayList<EstudiantesEntity>) estudiantesRepository.findAll();
    }
}
