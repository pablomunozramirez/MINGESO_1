package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.repositories.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
}
