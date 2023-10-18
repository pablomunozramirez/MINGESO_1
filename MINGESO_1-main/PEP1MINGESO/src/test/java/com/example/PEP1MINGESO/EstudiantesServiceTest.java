package com.example.PEP1MINGESO;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import com.example.PEP1MINGESO.services.CuotaService;
import com.example.PEP1MINGESO.services.EstudiantesService;
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

public class EstudiantesServiceTest {
    @Mock
    private EstudiantesRepository estudiantesRepository;

    @Mock
    private CuotaRepository cuotaRepository;

    @InjectMocks
    private EstudiantesService estudiantesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crearEstudiante1Test(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);


        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.empty());
        EstudiantesEntity estudiante = estudiantesService.crearEstudiante1(estudianteMock);
        assertEquals("20627890-0", estudiante.getRut());
    }
    @Test
    public void existeEstudianteTest(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock));

        int resultado = estudiantesService.existeEstudiante("20627890-0");
        assertEquals(1, resultado, 0);
    }

    @Test
    public void existeEstudianteTest2(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.empty());

        int resultado = estudiantesService.existeEstudiante("20627890-1");
        assertEquals(0, resultado, 0);
    }

    @Test
    public void obtenerTipoTest(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock));

        int tipo = estudiantesService.obtenerTipo("20627890-0");
        assertEquals(1, tipo, 0);
    }

    @Test
    public void obtenerTipoTest2(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("2");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock));

        int tipo = estudiantesService.obtenerTipo("20627890-0");
        assertEquals(2, tipo, 0);
    }
    @Test
    public void obtenerTipoTest3(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("3");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock));

        int tipo = estudiantesService.obtenerTipo("20627890-0");
        assertEquals(3, tipo, 0);
    }
     @Test
    public void calcularArancelTest(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        double arancel = estudiantesService.calcularArancel(estudianteMock);
        assertEquals(1200000, arancel, 0);
    }

    @Test
    public void calcularArancelTest2(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("2");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2022);

        double arancel = estudiantesService.calcularArancel(estudianteMock);
        assertEquals(1242000.0, arancel, 0);
    }

    @Test
    public void calcularArancelTest3(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("3");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2019);

        double arancel = estudiantesService.calcularArancel(estudianteMock);
        assertEquals(1440000.0, arancel, 0);
    }

    @Test
    public void calcularArancelTest4(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("3");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2023);

        double arancel = estudiantesService.calcularArancel(estudianteMock);
        assertEquals(1275000.0, arancel, 0);
    }


    @Test
    public void buscarNombreByRutTest(){
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);


        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock));


        String nombre = estudiantesService.buscarNombreByRut("20627890-0");
        assertEquals("Pablo Muñoz", nombre);
    }


}
