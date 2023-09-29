package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.repositories.PruebasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PruebasService {
    @Autowired
    PruebasRepository pruebasRepository;
}
