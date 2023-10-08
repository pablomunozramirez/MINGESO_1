package com.example.PEP1MINGESO.controllers;


import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.services.CuotaService;
import com.example.PEP1MINGESO.services.EstudiantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mostrar")
public class MostrarCuotasController {
    @Autowired
    CuotaService cuotaService;
    @Autowired
    EstudiantesService estudiantesService;

    @GetMapping("/rut")
    public String obtenerRut(){
        return ("/mostrar/rut");
    }

    @PostMapping("/rut")
    public String obtenerRutParaMostrar(@RequestParam String rut, Model model, RedirectAttributes redirectAttributes){
        try {
            List<Integer> cuotas = cuotaService.obternerCuotaPorRut(rut);
            redirectAttributes.addFlashAttribute("cuotas", cuotas);
            String nombre = estudiantesService.buscarNombreByRut(rut);
            redirectAttributes.addFlashAttribute("nombre", nombre);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Error al crear el estudiante: " + e.getMessage());
        }
        return ("redirect:/mostrar/cuota");
    }

    @GetMapping("/cuota")
    public String mostrarCuotas (@ModelAttribute("rut") String rut, @ModelAttribute("cuotas") List<Integer> cuotas){

        return ("/mostrar/cuota");
    }
}
