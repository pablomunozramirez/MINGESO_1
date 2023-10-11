package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.time.LocalDate;
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

    public void generarCuota (String rut, String numeroCuota){
        int numero_Cuotas = Integer.parseInt(numeroCuota);
        if (numero_Cuotas == 1){
            int valorCuotas = 1500000/2;
            CuotaEntity cuota = new CuotaEntity();
            cuota.setNumero_cuota(1);
            cuota.setRutCuota(rut);
            cuota.setMonto(valorCuotas);
            cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
            cuota.setPagada("Pagada");
            cuotaRepository.save(cuota);
        }else{
            Optional<EstudiantesEntity> estudiante = estudiantesRepository.findById(rut);
            EstudiantesEntity estudiante1 = estudiante.get();
            double arancelDescuento = estudiantesService.calcularArancel(estudiante1);
            System.out.println(arancelDescuento);
            int valorCuotas = (int)arancelDescuento/numero_Cuotas;
            LocalDate fecha = LocalDate.parse("2023-04-10");
            for (int i = 1; i<= numero_Cuotas; i++){
                CuotaEntity cuota = new CuotaEntity();
                cuota.setNumero_cuota(i);
                cuota.setRutCuota(rut);
                cuota.setMonto(valorCuotas);
                cuota.setPagada("Pendiente");
                cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
                LocalDate fechaCuota = fecha.plusMonths(i-1);
                cuota.setFechaDePago(fechaCuota);
                cuotaRepository.save(cuota);
            }
        }
    }

    public List<CuotaEntity> obternerCuotaPorRut(String rut){
        List <CuotaEntity> cuotas = cuotaRepository.findByRutCuota(rut);
        return cuotas;
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
}
