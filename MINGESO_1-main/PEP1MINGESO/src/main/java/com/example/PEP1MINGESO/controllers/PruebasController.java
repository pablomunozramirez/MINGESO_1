package com.example.PEP1MINGESO.controllers;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import com.example.PEP1MINGESO.services.PruebasService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {
    @Autowired
    PruebasService pruebasService;

    @GetMapping("/mostrar")
    public String mostrarDatos(Model model) {
        model.addAttribute("datos", pruebasService.obtenerData());
        return "mostrar";
    }

    @GetMapping("/cargar")
    public String cargarPagina() {
        return "pruebas/cargar";
    }

    @PostMapping("/cargar")
    public String cargarDatos(@RequestParam("file") MultipartFile file, Model model) {
        String mensaje = pruebasService.guardar(file);
        model.addAttribute("mensaje", mensaje);
        return "redirect:/";
    }

    @GetMapping("/mostrarpruebas")
    public String mostrarPruebas(Model model){
        ArrayList<PruebasEntity> pruebas = pruebasService.obtenerpruebas();
        model.addAttribute("pruebas",pruebas);
        return ("pruebas/mostrarpruebas");
    }


}
