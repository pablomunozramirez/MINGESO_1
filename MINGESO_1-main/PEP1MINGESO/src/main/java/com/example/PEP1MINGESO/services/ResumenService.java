package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import com.example.PEP1MINGESO.entities.ResumenEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import com.example.PEP1MINGESO.repositories.PruebasRepository;
import com.example.PEP1MINGESO.repositories.ResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResumenService {
    @Autowired
    ResumenRepository resumenRepository;
    @Autowired
    EstudiantesRepository estudiantesRepository;
    @Autowired
    CuotaRepository cuotaRepository;
    @Autowired
    PruebasService pruebasService;
    @Autowired
    PruebasRepository pruebasRepository;



    public ArrayList<CuotaEntity> aplicarDescuentos() {
        ArrayList<EstudiantesEntity> estudiantes = (ArrayList<EstudiantesEntity>) estudiantesRepository.findAll();
        ArrayList<CuotaEntity> cuotaResultado = new ArrayList<>();
        for (EstudiantesEntity estudiante : estudiantes) {
            String rutEstudiante = estudiante.getRut();
            long numeroCuotas = cuotaRepository.countByRutCuota(rutEstudiante);
            if (numeroCuotas == 1) {
                int promedioPruebas = pruebasService.calcularPromedioPruebas(rutEstudiante);
                double descuento = calcularDescuento(promedioPruebas);

                ArrayList<CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rutEstudiante);
                if (!cuotas.isEmpty()) {
                    CuotaEntity cuota = cuotas.get(0);
                    double monto = cuota.getMonto();
                    monto = monto * descuento;
                    cuota.setMontoDescuentoP((int) monto);
                    cuotaRepository.save(cuota);
                    cuotaResultado.add(cuota);
                }
            } else {
                if (existenPruebasDeEstudiante(rutEstudiante)) {
                    calcularDescuentosCuotas(rutEstudiante);
                    calcularInteresCuotas(rutEstudiante);
                } else {
                    calcularInteresCuotas(rutEstudiante);
                }
            }
        }
        return cuotaResultado;
    }


    // La función debe calcular el promedio de puntaje de un mes y calcular el descuento, luego ver si está atrasada
    //Si lo está se debe de tener interés.
    public ArrayList<CuotaEntity> calcularDescuentosCuotas(String rut){
        ArrayList<PruebasEntity> pruebasEstudiante = pruebasRepository.findByRutPrueba(rut);
        ArrayList<CuotaEntity> cuotasEstudiante = cuotaRepository.findByRutCuota(rut);
        ArrayList<CuotaEntity> cuotaResultado = new ArrayList<>();
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
                cuota.setMontoDescuentoP((int) monto);
                cuotaRepository.save(cuota);
            }
            cuotaResultado.add(cuota);
        }
        return cuotaResultado;
    }

    public ArrayList<CuotaEntity> calcularInteresCuotas(String rut) {
        ArrayList<CuotaEntity> cuotasEstudiante = cuotaRepository.findByRutCuota(rut);
        ArrayList<CuotaEntity> cuotasResultado = new ArrayList<>();

        for (CuotaEntity cuota : cuotasEstudiante) {
            long mesesDeDiferencia = ChronoUnit.MONTHS.between(cuota.getFechaDePago(), LocalDate.now());
            int meses = Math.toIntExact(mesesDeDiferencia);

            double monto = cuota.getMontoDescuentoP();
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
            cuota.setMontoDescuentoI((int) montoFinal);
            cuotaRepository.save(cuota);
            cuotasResultado.add(cuota);
        }
        return cuotasResultado;
    }

    public ArrayList<ResumenEntity> resumenArancel (){
        ArrayList<EstudiantesEntity> estudiantes = (ArrayList<EstudiantesEntity>) estudiantesRepository.findAll();
        ArrayList <ResumenEntity> resumen = new ArrayList<>();
        for(EstudiantesEntity estudiante : estudiantes){
            String rutEstudiante = estudiante.getRut();
            ResumenEntity resumenEstudiante = generarResumenEstudiante(rutEstudiante);
            resumen.add(resumenEstudiante);
        }
        return  resumen;
    }


    public ResumenEntity generarResumenEstudiante (String rut){
        ResumenEntity resumen;
        if (resumenRepository.existsByRutEstudiante(rut)) {
            resumen = resumenRepository.findByRutEstudiante(rut);
            resumen.setRutEstudiante(rut);
            resumen.setNumeroExamenesRendidos(numeroExamenesrendidos(rut));
            resumen.setPromedioExamenes(pruebasService.calcularPromedioPruebas(rut));
            resumen.setMontoTotalArancel(montoTotalArancel(rut));
            resumen.setTipoPago(tipoPago(rut));
            resumen.setNumeroTotalCuotas(numeroTotalCuotas(rut));
            resumen.setMontoTotalPagado(montoTotalPagado(rut));
            resumen.setFechaUltimoPago(fechaUltimoPago(rut));
            resumen.setSaldoPorPagar(saldoPorPagar(rut));
            resumen.setNumeroCuotasConRetraso(cuotaConRetraso(rut));
        }else {
            resumen = new ResumenEntity();
            resumen.setRutEstudiante(rut);
            resumen.setNumeroExamenesRendidos(numeroExamenesrendidos(rut));
            resumen.setPromedioExamenes(pruebasService.calcularPromedioPruebas(rut));
            resumen.setMontoTotalArancel(montoTotalArancel(rut));
            resumen.setTipoPago(tipoPago(rut));
            resumen.setNumeroTotalCuotas(numeroTotalCuotas(rut));
            resumen.setMontoTotalPagado(montoTotalPagado(rut));
            resumen.setFechaUltimoPago(fechaUltimoPago(rut));
            resumen.setSaldoPorPagar(saldoPorPagar(rut));
            resumen.setNumeroCuotasConRetraso(cuotaConRetraso(rut));
        }
        resumenRepository.save(resumen);
        return resumen;
    }

    public int numeroExamenesrendidos(String rut){
        long a = pruebasRepository.countByRutPrueba(rut);
        int b = (int)a;
        return b;
    }

    public double montoTotalArancel(String rut){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        double arancelTotal=0;
        for(CuotaEntity cuota : cuotas){
            arancelTotal = arancelTotal + cuota.getMontoDescuentoI();
        }
        return arancelTotal;
    }
    public String tipoPago(String rut){
        ArrayList <CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        int n = cuotas.size();
        String resultado = "Cuotas";
        if (n==1){
            for(CuotaEntity cuota : cuotas){
                if (cuota.getMonto() == 750000) {
                    resultado = "Contado";
                    break;
                }
            }
        }
        return resultado;
    }
    public int numeroTotalCuotas(String rut){
        return (int) cuotaRepository.countByRutCuota(rut);
    }

    public double montoTotalPagado(String rut){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        double montoPagado=0;
        for (CuotaEntity cuota : cuotas){
            if (cuota.getPagada().equals("Pagada")){
                montoPagado = montoPagado + cuota.getMontoDescuentoI();
            }
        }
        return montoPagado;
    }

    public int cuotaConRetraso(String rut){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        int cuotasAtrasadas=0;
        LocalDate fechaActual = LocalDate.now();
        for (CuotaEntity cuota : cuotas){
            int resultado = fechaActual.compareTo(cuota.getFechaDePago());
            if (cuota.getPagada().equals("Pendiente") && resultado >0){
                cuotasAtrasadas = cuotasAtrasadas + 1;
            }
        }
        return cuotasAtrasadas;
    }

    public LocalDate fechaUltimoPago(String rut){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        LocalDate fechaActual = LocalDate.now();
        LocalDate fecha = fechaActual.plusYears(0).withMonth(4).withDayOfMonth(10);
        for(CuotaEntity cuota: cuotas){
            if(cuota.getPagada().equals("Pagada")){
                int resultado = fecha.compareTo(cuota.getFechaDePago());
                if (resultado < 0){
                    fecha = cuota.getFechaDePago();
                }
            }
        }
        return fecha;
    }

    public double saldoPorPagar(String rut){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        double montoPorPagar=0;
        for (CuotaEntity cuota : cuotas){
            if (cuota.getPagada().equals("Pendiente")){
                montoPorPagar = montoPorPagar + cuota.getMontoDescuentoI();
            }
        }
        return montoPorPagar;
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
        double cantidadPruebas = pruebasRepository.countByRutPrueba(rut);
        return cantidadPruebas > 0;
    }
}
