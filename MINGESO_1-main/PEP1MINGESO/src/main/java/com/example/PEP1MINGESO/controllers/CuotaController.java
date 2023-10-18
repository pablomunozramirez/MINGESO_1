package com.example.PEP1MINGESO.controllers;

import com.example.PEP1MINGESO.entities.CuotaEntity;
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

import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@Controller
@RequestMapping("/cuota")
public class CuotaController {
    @Autowired
    CuotaService cuotaService;
    @Autowired
    EstudiantesService estudiantesService;

    @GetMapping("/generar")
    public String vistaGenerarCuotas (){
        return ("cuota/generar");
    }

    @PostMapping("/generar")
    public String generarCuota1(@RequestParam String rut, @RequestParam String tipoPago,Model model, RedirectAttributes redirectAttributes) {
        int a = estudiantesService.existeEstudiante(rut);
        int b = cuotaService.existenCuotas(rut);
        try {

            redirectAttributes.addFlashAttribute("mensaje", "Estudiante creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el estudiante: " + e.getMessage());
        }
        if ("contado".equals(tipoPago) && a==1 && b==0) {
            cuotaService.generarCuota(rut, "1");
            return "redirect:/";
        } else if ("cuotas".equals(tipoPago)&& a==1 && b==0)  {
            int tipo = estudiantesService.obtenerTipo(rut);
            if (tipo == 1){
                redirectAttributes.addFlashAttribute("rut", rut);
                return "redirect:/cuota/municipal";
            } else if (tipo==2) {
                redirectAttributes.addFlashAttribute("rut", rut);
                return "redirect:/cuota/subvencionado";
            }else {
                redirectAttributes.addFlashAttribute("rut", rut);
                return "redirect:/cuota/privado";
            }
        }else{
            return "redirect:/";
        }
    }

    @GetMapping("/subvencionado")
    public String mostrarCuotasSubvencionado() {
        System.out.println("subvencionado");
        return ("cuota/subvencionado");
    }
    @PostMapping("/subvencionado")
    public String cuotassubvencionado (@ModelAttribute("rut") String rut,@RequestParam String numero_Cuotas ,Model model){
        cuotaService.generarCuota(rut, numero_Cuotas);
        return "redirect:/";
    }

    @GetMapping("/privado")
    public String mostrarCuotasPrivado(){
        return ("cuota/privado");
    }

    @PostMapping("/privado")
    public String cuotasPrivado (@ModelAttribute("rut") String rut,@RequestParam(value = "numero_Cuotas") String numero_Cuotas ,Model model){
        cuotaService.generarCuota(rut, numero_Cuotas);
        return "redirect:/";
    }

    @GetMapping("/municipal")
    public String mostrarCuotasMunicipal() {
        return "cuota/municipal";
    }
    @PostMapping("/municipal")
    public String cuotasMunicipal (@RequestParam String rut, @RequestParam String numero_Cuotas,Model model){
        cuotaService.generarCuota(rut, numero_Cuotas);
        return "redirect:/";
    }

}
