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

public class CuotaServiceTest {

    @Mock
    private EstudiantesRepository estudiantesRepository;

    @Mock
    private CuotaRepository cuotaRepository;

    @Mock
    private EstudiantesService estudiantesService;

    @InjectMocks
    private CuotaService cuotaService;

    // Usa la anotación @BeforeEach para inicializar los mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerarCuota() {
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("Municipal");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of((estudianteMock)));
        when(estudiantesService.calcularArancel(eq(estudianteMock))).thenReturn(1200000.0);

        ArrayList<CuotaEntity> cuotas = cuotaService.generarCuota("20627890-0", "10");

        assertNotNull(cuotas);
        for (CuotaEntity cuota : cuotas) {
            assertEquals(120000, cuota.getMonto(), 0);
        }
    }

    @Test
    public void testGenerarCuota2() {
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("Municipal");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);

        when(estudiantesRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock));
        when(estudiantesService.calcularArancel(eq(estudianteMock))).thenReturn(1200000.0);

        ArrayList<CuotaEntity> cuotas = cuotaService.generarCuota("20627890-0", "1");

        assertNotNull(cuotas);
        for (CuotaEntity cuota : cuotas) {
        assertEquals(750000 , cuota.getMonto(), 0);
        }
    }

    @Test
    public void obternerCuotaPorRutTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(20023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(20023, 4, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(20023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(20023, 4, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList();
        cuotas.add(cuota1);
        cuotas.add(cuota2);

        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        ArrayList<CuotaEntity> cuotas2 = cuotaService.obternerCuotaPorRut("20627890-0");
        assertEquals(2, cuotas2.size(), 0);
    }

    @Test
    public void existenCuotasTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(20023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(20023, 4, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        when(cuotaRepository.existsByRutCuota(anyString())).thenReturn(Boolean.TRUE);
        int existe = cuotaService.existenCuotas("20627890-0");
        assertEquals(1, existe, 0);
    }

    @Test
    public void cambiarEstadoDePagoCuotaTest (){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(20023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(20023, 4, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        when(cuotaRepository.findById(anyLong())).thenReturn(Optional.of(cuota1));
        CuotaEntity estado = cuotaService.cambiarEstadoDePagoCuota(1L);
        assertEquals("Pendiente", estado.getPagada());

    }
}