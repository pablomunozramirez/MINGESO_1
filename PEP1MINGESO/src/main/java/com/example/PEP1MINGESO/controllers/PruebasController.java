package com.example.PEP1MINGESO.controllers;

import com.example.PEP1MINGESO.services.PruebasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PruebasController {
    @Autowired
    PruebasService pruebasService;
}
