package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import com.example.PEP1MINGESO.repositories.PruebasRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
    @Autowired
    EstudiantesService estudiantesService;
    @Autowired
    EstudiantesRepository estudiantesRepository;
    @Autowired
    PruebasRepository pruebasRepository;
    @Autowired
    PruebasService pruebasService;

    public void generarCuota (String rut, String numeroCuota){
        int numero_Cuotas = Integer.parseInt(numeroCuota);
        if (numero_Cuotas == 1){
            int valorCuotas = 1500000/2;
            CuotaEntity cuota = new CuotaEntity();
            cuota.setNumero_cuota(1);
            cuota.setRutCuota(rut);
            cuota.setMonto(valorCuotas);
            cuota.setMontoDescuento(valorCuotas);
            cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
            cuota.setPagada("Pagada");
            cuotaRepository.save(cuota);
        }else{
            Optional<EstudiantesEntity> estudiante = estudiantesRepository.findById(rut);
            EstudiantesEntity estudiante1 = estudiante.get();
            double arancelDescuento = estudiantesService.calcularArancel(estudiante1);
            int valorCuotas = (int)arancelDescuento/numero_Cuotas;
            LocalDate fecha = LocalDate.parse("2023-04-10");
            for (int i = 1; i<= numero_Cuotas; i++){
                CuotaEntity cuota = new CuotaEntity();
                cuota.setNumero_cuota(i);
                cuota.setRutCuota(rut);
                cuota.setMonto(valorCuotas);
                cuota.setMontoDescuento(valorCuotas);
                cuota.setPagada("Pendiente");
                cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
                LocalDate fechaCuota = fecha.plusMonths(i-1);
                cuota.setFechaDePago(fechaCuota);
                cuotaRepository.save(cuota);
            }
        }
    }

    public List<CuotaEntity> obternerCuotaPorRut(String rut){
        return cuotaRepository.findByRutCuota(rut);
    }

    public List<String> cuotaPagada(String rut){
        List <CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        return cuotas.stream().map(CuotaEntity::getPagada).toList();
    }

    public int existenCuotas(String rut) {
        boolean existenCuotas = cuotaRepository.existsByRutCuota(rut);

        // Si existen cuotas, retornar 1; de lo contrario, retornar 0
        return existenCuotas ? 1 : 0;
    }

    public void cambiarEstadoDePagoCuota(Long id_cuota){
        LocalDate fechaActual = LocalDate.now();
        int diaActual = fechaActual.getDayOfMonth();
        if (diaActual >= 5 && diaActual <= 10) {
            Optional<CuotaEntity> cuota = cuotaRepository.findById(id_cuota);
            if (cuota.isPresent()) {
                CuotaEntity cuota1 = cuota.get();
                cuota1.setPagada("Pagada");
                cuotaRepository.save(cuota1);
            } else {
                System.out.println("No existe la cuota");
            }
        }
    }


    public void aplicarDescuentos(){
        List<EstudiantesEntity> estudiantes = (List<EstudiantesEntity>) estudiantesRepository.findAll();
        for (EstudiantesEntity estudiante : estudiantes) {
            String rutEstudiante = estudiante.getRut();
            long numeroCuotas = cuotaRepository.countByRutCuota(rutEstudiante);
            if (numeroCuotas == 1) {
                int promedioPruebas = pruebasService.calcularPromedioPruebas(rutEstudiante);
                double descuento = calcularDescuento(promedioPruebas);
                CuotaEntity cuota = (CuotaEntity) cuotaRepository.findByRutCuota(rutEstudiante);
                double monto = cuota.getMonto();
                monto = monto * descuento;
                cuota.setMontoDescuento((int) monto);
                cuotaRepository.save(cuota);
            } else {
                if (existenPruebasDeEstudiante(rutEstudiante)) {
                    calcularDescuentosCuotas(rutEstudiante);
                }
                if (existenCuotasDeEstudiante(rutEstudiante)) {
                    calcularInteresCuotas(rutEstudiante);
                }
            }
        }
    }


    // La función debe calcular el promedio de puntaje de un mes y calcular el descuento, luego ver si está atrasada
    //Si lo está se debe de tener interés.
    public void calcularDescuentosCuotas(String rut){
        List<PruebasEntity> pruebasEstudiante = pruebasRepository.findByRutPrueba(rut);
        List<CuotaEntity> cuotasEstudiante = cuotaRepository.findByRutCuota(rut);
        for (CuotaEntity cuota : cuotasEstudiante) {
            LocalDate fechaCuota = cuota.getFechaDePago();
            int promedio = 0;
            int total = 0;
            for (PruebasEntity prueba : pruebasEstudiante) {
                if (mismoMesYAnio(fechaCuota, prueba.getFecha_examen())) {
                    promedio = promedio + prueba.getPuntaje();
                    total = total + 1;

                }
            }
            if (total != 0 && promedio != 0) {
                double promedioPruebas = promedio / total;
                double descuento = calcularDescuento((int) promedioPruebas);
                double monto = (double) cuota.getMonto();
                monto = monto * descuento;
                cuota.setMontoDescuento((int) monto);
                System.out.println(monto);
                cuotaRepository.save(cuota);
            }
        }
    }

    public void calcularInteresCuotas(String rut) {
        List<CuotaEntity> cuotasEstudiante = cuotaRepository.findByRutCuota(rut);

        for (CuotaEntity cuota : cuotasEstudiante) {
            long mesesDeDiferencia = ChronoUnit.MONTHS.between(cuota.getFechaDePago(), LocalDate.now());
            int meses = Math.toIntExact(mesesDeDiferencia);

            double monto = cuota.getMontoDescuento();
            double montoFinal;

            if (meses == 1) {
                montoFinal = monto * 1.03;
            } else if (meses == 2) {
                montoFinal = monto * 1.06;
            } else if (meses == 3) {
                montoFinal = monto * 1.09;
            } else if (meses > 3) {
                montoFinal = monto * 1.15;
            } else {
                montoFinal = monto * 1;
            }
            cuota.setMontoDescuento((int) montoFinal);
            cuotaRepository.save(cuota);
        }
    }

    public boolean mismoMesYAnio(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.getMonth().equals(fecha2.getMonth()) && fecha1.getYear() == fecha2.getYear();
    }

    public double calcularDescuento(int promedio){
        double descuento;
        if (promedio <= 1000 && promedio >=950){
            descuento = 0.9;
        } else if (promedio <= 949 && promedio >=900) {
            descuento = 0.95;
        } else if (promedio <= 899 && promedio >=850) {
            descuento = 0.98;
        }else{
            descuento = 1;
        }
        return descuento;
    }

    public boolean existenPruebasDeEstudiante(String rut) {
        long cantidadPruebas = pruebasRepository.countByRutPrueba(rut);
        return cantidadPruebas > 0;
    }

    public boolean existenCuotasDeEstudiante(String rut) {
        long cantidadCuotas = cuotaRepository.countByRutCuota(rut);
        return cantidadCuotas > 0;
    }
}
