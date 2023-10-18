package com.example.PEP1MINGESO;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import com.example.PEP1MINGESO.entities.ResumenEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import com.example.PEP1MINGESO.repositories.PruebasRepository;
import com.example.PEP1MINGESO.repositories.ResumenRepository;
import com.example.PEP1MINGESO.services.CuotaService;
import com.example.PEP1MINGESO.services.EstudiantesService;
import com.example.PEP1MINGESO.services.PruebasService;
import com.example.PEP1MINGESO.services.ResumenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ResumenServiceTest {
    @Mock
    private EstudiantesRepository estudiantesRepository;

    @Mock
    private CuotaRepository cuotaRepository;

    @Mock
    private ResumenRepository resumenRepository;

    @Mock
    private PruebasRepository pruebasRepository;

    @Mock
    private PruebasService pruebasService;

    @InjectMocks
    private ResumenService resumenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void existenPruebasDeEstudianteTest(){
        when(pruebasRepository.countByRutPrueba(anyString())).thenReturn(2L);

        boolean resultado = resumenService.existenPruebasDeEstudiante("20627890-0");
        assertTrue(resultado);

    }
    @Test
    public void calcularDescuentoTest2(){

        double descuento = resumenService.calcularDescuento(925);
        assertEquals(0.95, descuento, 0);
    }

    @Test
    public void calcularDescuentoTest3(){

        double descuento = resumenService.calcularDescuento(875);
        assertEquals(0.98, descuento, 0);
    }

    @Test
    public void calcularDescuentoTest4(){

        double descuento = resumenService.calcularDescuento(750);
        assertEquals(1, descuento, 0);
    }

    @Test
    public void calcularDescuentoTest(){

        double descuento = resumenService.calcularDescuento(975);
        assertEquals(0.9, descuento, 0);
    }

    @Test
    public void mismoMesYAnioTest(){
        boolean resultado = resumenService.mismoMesYAnio(LocalDate.of(20023, 6, 25), LocalDate.of(20023, 6, 1));
        assertTrue(resultado);
    }

    @Test
    public void saldoPorPagarTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);


        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        double deuda = resumenService.saldoPorPagar("20627890-0");

        assertEquals(240000, deuda, 0);
    }

    @Test
    public void fechaUltimoPagoTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pagada");
        cuota1.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pagada");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);

        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        LocalDate fecha = resumenService.fechaUltimoPago("20627890-0");
        assertEquals(LocalDate.of(2023, 6, 10), fecha);
    }
    @Test
    public void cuotaConRetrasoTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        int cuotasRetrasadas = resumenService.cuotaConRetraso("20627890-0");
        assertEquals(2, cuotasRetrasadas, 0);
    }
    @Test
    public void montoTotalPagadoTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        double montoPagado = resumenService.montoTotalPagado("20627890-0");
        assertEquals(120000, montoPagado, 0);
    }

    @Test
    public void numeroTotalCuotasTest(){
        when(cuotaRepository.countByRutCuota(anyString())).thenReturn(3L);

        int cuotasTotal = resumenService.numeroTotalCuotas("20627890-0");
        assertEquals(3, cuotasTotal, 0);
    }
    @Test
    public void tipoPagoTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        String tipo = resumenService.tipoPago("20627890-0");
        assertEquals("Cuotas", tipo);
    }

    @Test
    public void tipoPagoTest2(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(750000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota1.setMontoDescuentoP(750000);
        cuota1.setMontoDescuentoI(750000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        String tipo = resumenService.tipoPago("20627890-0");
        assertEquals("Contado", tipo);
    }
    @Test
    public void montoTotalArancelTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        double totalArancel = resumenService.montoTotalArancel("20627890-0");
        assertEquals(360000, totalArancel, 0);
    }
    @Test
    public void numeroExamenesrendidosTest(){

        when(pruebasRepository.countByRutPrueba(anyString())).thenReturn(3L);
        int pruebasRendidas = resumenService.numeroExamenesrendidos("20627890-0");
        assertEquals(3, pruebasRendidas);

    }

    @Test
    public void generarResumenEstudianteTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);

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


        when(resumenRepository.existsByRutEstudiante(anyString())).thenReturn(false);
        when(pruebasRepository.countByRutPrueba(anyString())).thenReturn(2L);
        when(pruebasService.calcularPromedioPruebas(anyString())).thenReturn(750);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        when(cuotaRepository.countByRutCuota(anyString())).thenReturn(3L);


        ResumenEntity arancel = resumenService.generarResumenEstudiante("20627890-0");
        assertEquals("20627890-0", arancel.getRutEstudiante());
        assertEquals(2, arancel.getNumeroExamenesRendidos(),0);
        assertEquals(750, arancel.getPromedioExamenes(),0);
        assertEquals(360000, arancel.getMontoTotalArancel());
        assertEquals("Cuotas", arancel.getTipoPago());
        assertEquals(3, arancel.getNumeroTotalCuotas());
        assertEquals(120000, arancel.getMontoTotalPagado(),0);
        assertEquals(LocalDate.of(2023, 4, 10), arancel.getFechaUltimoPago());
        assertEquals(240000, arancel.getSaldoPorPagar());
        assertEquals(2, arancel.getNumeroCuotasConRetraso());

    }

    @Test
    public void generarResumenEstudianteTest2(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);

        ResumenEntity resumen = new ResumenEntity();
        when(resumenRepository.existsByRutEstudiante(anyString())).thenReturn(true);
        when(resumenRepository.findByRutEstudiante(anyString())).thenReturn(resumen);
        when(pruebasRepository.countByRutPrueba(anyString())).thenReturn(2L);
        when(pruebasService.calcularPromedioPruebas(anyString())).thenReturn(750);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        when(cuotaRepository.countByRutCuota(anyString())).thenReturn(3L);

        ResumenEntity arancel = resumenService.generarResumenEstudiante("20627890-0");
        assertEquals("20627890-0", arancel.getRutEstudiante());
        assertEquals(2, arancel.getNumeroExamenesRendidos(), 0);
        assertEquals(750, arancel.getPromedioExamenes());
        assertEquals(360000, arancel.getMontoTotalArancel());
        assertEquals("Cuotas",arancel.getTipoPago());
        assertEquals(3, arancel.getNumeroTotalCuotas());
        assertEquals(120000, arancel.getMontoTotalPagado());
        assertEquals(LocalDate.of(2023, 4, 10), arancel.getFechaUltimoPago());
        assertEquals(240000, arancel.getSaldoPorPagar());
        assertEquals(2, arancel.getNumeroCuotasConRetraso());
    }
    @Test
    public void resumenArancelTest(){
        ArrayList<EstudiantesEntity> estudiantes = new ArrayList<>();
        EstudiantesEntity estudianteMock = new EstudiantesEntity();
        estudianteMock.setRut("20627890-0");
        estudianteMock.setNombre("Pablo");
        estudianteMock.setApellidos("Muñoz");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
        estudianteMock.setTipo_colegio("1");
        estudianteMock.setNombre_colegio("Colegio XYZ");
        estudianteMock.setAnio_egreso(2018);
        estudiantes.add(estudianteMock);

        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setRutCuota("20627890-0");
        cuota2.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota2.setNumero_cuota(1);
        cuota2.setMonto(120000);
        cuota2.setPagada("Pendiente");
        cuota2.setFechaDePago(LocalDate.of(2023, 5, 10));
        cuota2.setMontoDescuentoP(120000);
        cuota2.setMontoDescuentoI(120000);

        CuotaEntity cuota3 = new CuotaEntity();
        cuota3.setRutCuota("20627890-0");
        cuota3.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota3.setNumero_cuota(1);
        cuota3.setMonto(120000);
        cuota3.setPagada("Pagada");
        cuota3.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota3.setMontoDescuentoP(120000);
        cuota3.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);
        cuotas.add(cuota3);

        ResumenEntity resumen = new ResumenEntity();

        when(estudiantesRepository.findAll()).thenReturn(estudiantes);

        when(resumenRepository.existsByRutEstudiante(anyString())).thenReturn(true);
        when(resumenRepository.findByRutEstudiante(anyString())).thenReturn(resumen);
        when(pruebasRepository.countByRutPrueba(anyString())).thenReturn(2L);
        when(pruebasService.calcularPromedioPruebas(anyString())).thenReturn(750);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        when(cuotaRepository.countByRutCuota(anyString())).thenReturn(3L);

        ArrayList<ResumenEntity> arancelTodos = resumenService.resumenArancel();
        assertEquals(1, arancelTodos.size());
    }

    @Test
    public void calcularInteresCuotasTest(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 6, 14));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);

        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        ArrayList<CuotaEntity> cuotasResultado = resumenService.calcularInteresCuotas("20627890-0");
        for (CuotaEntity cuota: cuotasResultado){
            assertEquals(138000, cuota.getMontoDescuentoI());
        }
    }

    @Test
    public void calcularInteresCuotasTest2(){
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 7, 14));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);

        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        ArrayList<CuotaEntity> cuotasResultado = resumenService.calcularInteresCuotas("20627890-0");
        for (CuotaEntity cuota: cuotasResultado){
            assertEquals(130800, cuota.getMontoDescuentoI());
        }
    }
    @Test
    public void calcularInteresCuotasTest3() {
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 8, 14));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        ArrayList<CuotaEntity> cuotas = new ArrayList<>();
        cuotas.add(cuota1);

        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        ArrayList<CuotaEntity> cuotasResultado = resumenService.calcularInteresCuotas("20627890-0");
        for (CuotaEntity cuota : cuotasResultado) {
            assertEquals(127200, cuota.getMontoDescuentoI());
        }
    }
    @Test
    public void calcularInteresCuotasTest4() {
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pendiente");
        cuota1.setFechaDePago(LocalDate.of(2023, 9, 14));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);

        ArrayList<CuotaEntity> cuotas = new ArrayList<>();
        cuotas.add(cuota1);

        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

        ArrayList<CuotaEntity> cuotasResultado = resumenService.calcularInteresCuotas("20627890-0");
        for (CuotaEntity cuota : cuotasResultado) {
            assertEquals(123600, cuota.getMontoDescuentoI());
        }
    }
    @Test
    public void calcularDescuentosCuotasTest(){
        PruebasEntity prueba = new PruebasEntity();
        prueba.setRutPrueba("20627890-0");
        prueba.setPuntaje(1000);
        prueba.setFecha_examen(LocalDate.of(2023, 4, 25));

        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        pruebas.add(prueba);

        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setRutCuota("20627890-0");
        cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
        cuota1.setNumero_cuota(1);
        cuota1.setMonto(120000);
        cuota1.setPagada("Pagada");
        cuota1.setFechaDePago(LocalDate.of(2023, 4, 10));
        cuota1.setMontoDescuentoP(120000);
        cuota1.setMontoDescuentoI(120000);
        ArrayList<CuotaEntity> cuotas= new ArrayList<>();
        cuotas.add(cuota1);

        when(pruebasRepository.findByRutPrueba(anyString())).thenReturn(pruebas);
        when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);
        ArrayList<CuotaEntity> cuotasResultado = resumenService.calcularDescuentosCuotas("20627890-0");
        for (CuotaEntity cuota : cuotasResultado) {
            assertEquals(108000, cuota.getMontoDescuentoP());
        }
    }
        @Test
        public void aplicarDescuento(){
            ArrayList<EstudiantesEntity> estudiantes = new ArrayList<>();
            EstudiantesEntity estudianteMock = new EstudiantesEntity();
            estudianteMock.setRut("20627890-0");
            estudianteMock.setNombre("Pablo");
            estudianteMock.setApellidos("Muñoz");
            estudianteMock.setFecha_nacimiento(LocalDate.of(2001, 1, 31));
            estudianteMock.setTipo_colegio("1");
            estudianteMock.setNombre_colegio("Colegio XYZ");
            estudianteMock.setAnio_egreso(2018);
            estudiantes.add(estudianteMock);

            CuotaEntity cuota1 = new CuotaEntity();
            cuota1.setRutCuota("20627890-0");
            cuota1.setFechaCuota(LocalDate.of(2023, 4, 1));
            cuota1.setNumero_cuota(1);
            cuota1.setMonto(120000);
            cuota1.setPagada("Pagada");
            cuota1.setFechaDePago(LocalDate.of(2023, 4, 10));
            cuota1.setMontoDescuentoP(120000);
            cuota1.setMontoDescuentoI(120000);
            ArrayList<CuotaEntity> cuotas= new ArrayList<>();
            cuotas.add(cuota1);

            when(estudiantesRepository.findAll()).thenReturn(estudiantes);
            when(cuotaRepository.countByRutCuota(anyString())).thenReturn(1L);
            when(pruebasService.calcularPromedioPruebas(anyString())).thenReturn(1000);
            when(cuotaRepository.findByRutCuota(anyString())).thenReturn(cuotas);

            ArrayList<CuotaEntity> cuotaR = resumenService.aplicarDescuentos();

            for (CuotaEntity cuota : cuotaR) {
                assertEquals(108000, cuota.getMontoDescuentoP());
            }
        }

}
