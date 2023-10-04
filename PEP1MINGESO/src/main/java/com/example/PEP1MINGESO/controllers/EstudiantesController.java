package com.example.PEP1MINGESO.controllers;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.services.EstudiantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estudiantes")
public class EstudiantesController {
    @Autowired
    EstudiantesService estudiantesService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearEstudiante(@RequestBody EstudiantesEntity estudianteRequest) {
        try {
            estudiantesService.crearEstudiante(estudianteRequest);
            return ResponseEntity.ok("Estudiante creado exitosamente");
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el estudiante: " + e.getMessage());
        }
    }
}
