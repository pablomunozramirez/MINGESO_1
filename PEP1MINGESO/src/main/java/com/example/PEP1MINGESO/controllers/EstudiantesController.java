package com.example.PEP1MINGESO.controllers;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.services.EstudiantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/estudiantes")
public class EstudiantesController {

    @Autowired
    EstudiantesService estudiantesService;

    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new EstudiantesEntity());
        return "estudiantes/crear";
    }

    @PostMapping("/guardar")
        public String guardarEstudiante(@ModelAttribute EstudiantesEntity estudianteRequest, RedirectAttributes redirectAttributes) {
        try {
            estudiantesService.crearEstudiante1(estudianteRequest);
            // Agregar mensaje de éxito a los atributos de redirección
            redirectAttributes.addFlashAttribute("mensaje", "Estudiante creado exitosamente");
        } catch (Exception e) {
            // Agregar mensaje de error a los atributos de redirección
            redirectAttributes.addFlashAttribute("error", "Error al crear el estudiante: " + e.getMessage());
        }
        // Redirigir a la página principal
        return "redirect:/";
    }
}
