package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public ArrayList<CuotaEntity> generarCuota (String rut, String numeroCuota){
        int numero_Cuotas = Integer.parseInt(numeroCuota);
        ArrayList<CuotaEntity> cuotas = new ArrayList<>();
        if (numero_Cuotas == 1){
            int valorCuotas = 1500000/2;
            CuotaEntity cuota = new CuotaEntity();
            cuota.setNumero_cuota(1);
            cuota.setMonto(valorCuotas);
            cuota.setMontoDescuentoP(valorCuotas);
            cuota.setMontoDescuentoI(valorCuotas);
            cuota.setPagada("Pagada");
            cuota.setRutCuota(rut);
            cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
            cuota.setFechaDePago(LocalDate.parse("2023-04-01"));
            cuotaRepository.save(cuota);
            cuotas.add(cuota);
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
                cuota.setMontoDescuentoP(valorCuotas);
                cuota.setMontoDescuentoI(valorCuotas);
                cuota.setPagada("Pendiente");
                cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
                LocalDate fechaCuota = fecha.plusMonths(i-1);
                cuota.setFechaDePago(fechaCuota);
                cuotaRepository.save(cuota);
                cuotas.add(cuota);

            }
        }
        return cuotas;
    }

    public ArrayList<CuotaEntity> obternerCuotaPorRut(String rut){
        return cuotaRepository.findByRutCuota(rut);
    }
/*
    public List<String> cuotaPagada(String rut){
        List <CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        return cuotas.stream().map(CuotaEntity::getPagada).toList();
    }
*/

    public int existenCuotas(String rut) {
        boolean existenCuotas = cuotaRepository.existsByRutCuota(rut);
        return existenCuotas ? 1 : 0;
    }

    public CuotaEntity cambiarEstadoDePagoCuota(Long id_cuota){
        LocalDate fechaActual = LocalDate.now();
        int diaActual = fechaActual.getDayOfMonth();
        Optional<CuotaEntity> cuotaOptional = cuotaRepository.findById(id_cuota);
        if (cuotaOptional.isPresent()) {
            CuotaEntity cuota1 = cuotaOptional.get();
            if (diaActual >= 5 && diaActual <= 10) {
                cuota1.setPagada("Pagada");
                cuotaRepository.save(cuota1);
                return cuota1;
            } else {
                System.out.println("Fecha actual no está en el rango permitido");
                return cuota1;
            }
        } else {
            System.out.println("No existe la cuota");
        }

        return null;
    }

}
