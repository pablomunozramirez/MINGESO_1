package com.example.PEP1MINGESO.controllers;


import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.ResumenEntity;
import com.example.PEP1MINGESO.services.CuotaService;
import com.example.PEP1MINGESO.services.ResumenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/arancel")
public class ArancelController {

    @Autowired
    ResumenService resumenService;

    @GetMapping("/generarArancel")
    public String mostrarVistaGenerarArancel() {
        return "/arancel/generarArancel";
    }

    @PostMapping("/generarArancel")
    public String generarArancel(){
        resumenService.aplicarDescuentos();
        return "redirect:/";
    }

    @GetMapping("/mostrarArancel")
    public String mostrarResumenArancel(Model model) {
        return "arancel/mostrarArancel";
    }

    @PostMapping("/mostrarArancel")
    public String resumenArancel(Model model, RedirectAttributes redirectAttributes) {
        List<ResumenEntity> resumenList = resumenService.resumenArancel();
        redirectAttributes.addFlashAttribute("resumenList", resumenList);

        return "redirect:/arancel/datosArancel";
    }

    @GetMapping("/datosArancel")
    public String mostrarArancel2 (@ModelAttribute("resumenList") List<ResumenEntity> resumenList) {

        return ("arancel/datosArancel");
    }
}
