package com.example.PEP1MINGESO;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import com.example.PEP1MINGESO.repositories.PruebasRepository;
import com.example.PEP1MINGESO.services.CuotaService;
import com.example.PEP1MINGESO.services.EstudiantesService;
import com.example.PEP1MINGESO.services.PruebasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations; // Importa esta clase

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


public class PruebasServiceTest {
    @Mock
    private EstudiantesRepository estudiantesRepository;

    @Mock
    private CuotaRepository cuotaRepository;

    @Mock
    private PruebasRepository pruebasRepository;
    @InjectMocks
    private PruebasService pruebasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calcularPromedioPruebasTest(){
        PruebasEntity prueba = new PruebasEntity();
        prueba.setRutPrueba("20627890-0");
        prueba.setPuntaje(1000);
        prueba.setFecha_examen(LocalDate.of(20023, 4, 25));

        PruebasEntity prueba1 = new PruebasEntity();
        prueba1.setRutPrueba("20627890-0");
        prueba1.setPuntaje(500);
        prueba1.setFecha_examen(LocalDate.of(20023, 5, 25));
        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        pruebas.add(prueba);
        pruebas.add(prueba1);

        when(pruebasRepository.findByRutPrueba(anyString())).thenReturn(pruebas);

        int promedio = pruebasService.calcularPromedioPruebas("20627890-0");
        assertEquals(750, promedio, 0);
    }
}
