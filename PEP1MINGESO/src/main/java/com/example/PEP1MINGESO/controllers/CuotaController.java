package com.example.PEP1MINGESO.controllers;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.services.CuotaService;
import com.example.PEP1MINGESO.services.EstudiantesService;
import com.fasterxml.jackson.core.io.BigIntegerParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.standard.inline.StandardTextInliner;

@Controller
@RequestMapping("/cuota")
public class CuotaController {
    @Autowired
    CuotaService cuotaService;
    @Autowired
    EstudiantesService estudiantesService;

    @GetMapping("/generar")
    public String vistaGenerarCuotas (){
        return ("/cuota/generar");
    }

    @PostMapping("/generar")
    public String generarCuota1(@RequestParam String rut, @RequestParam String tipoPago,Model model, RedirectAttributes redirectAttributes) {
        int a = estudiantesService.existeEstudiante(rut);
        if (a==1 && "contado".equals(tipoPago)) {
            cuotaService.generarCuota(rut, 1);
            model.addAttribute("mensaje", "Mensaje de éxito o información");
            return "redirect:/";
        } else if (a == 1 && "cuotas".equals(tipoPago)) {
            System.out.println("rut:"+rut);
            int tipo = estudiantesService.obtenerTipo(rut);
            if (tipo == 1){
                System.out.println("rut:"+rut);
                //redirectAttributes.addFlashAttribute("rut", rut);
                return "redirect:/cuota/municipal";
            } else if (tipo==2) {
                redirectAttributes.addFlashAttribute("rut", rut);
                return "redirect:/cuota/subencionado";
            }else {
                redirectAttributes.addFlashAttribute("rut", rut);
                return "redirect:/cuota/privado";
            }
        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/contado")
    public String generarContado (String rut, RedirectAttributes redirectAttributes){
        cuotaService.generarCuota(rut, 1);
        redirectAttributes.addFlashAttribute("mensaje", "Estudiante creado exitosamente");
        return "redirect:/";
    }

    @GetMapping("/subencionado")
    public String mostrarCuotasSubencionado(){
        return ("/cuota/subencionado");
    }
    @PostMapping("/subencionado")
    public String cuotasSubencionado (@ModelAttribute("rut") String rut,@RequestParam(value = "numero_Cuotas") Integer numero_Cuotas ,Model model){
        cuotaService.generarCuota(rut, numero_Cuotas);
        return "redirect:/";
    }

    @GetMapping("/privado")
    public String mostrarCuotasPrivado(){
        return ("/cuota/privado");
    }
    @PostMapping("/privado")
    public String cuotasPrivado (@ModelAttribute("rut") String rut,@RequestParam(value = "numero_Cuotas") Integer numero_Cuotas ,Model model){
        cuotaService.generarCuota(rut, numero_Cuotas);
        return "redirect:/";
    }

    @GetMapping("/municipal")
    public String mostrarCuotasMunicipal() {
        System.out.println("rut:");
        return "/cuota/municipal";
    }
    @PostMapping("/municipal")
    public String cuotasMunicipal (@RequestParam String rut, @RequestParam String numero_Cuotas,Model model){
        int numeroCuotas = Integer.parseInt(numero_Cuotas);
        System.out.println("rut:"+rut);
        cuotaService.generarCuota(rut, numeroCuotas);
        return "redirect:/";
    }



}
