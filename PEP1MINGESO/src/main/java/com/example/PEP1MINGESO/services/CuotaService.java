package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.CuotaEntity;
import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.repositories.CuotaRepository;
import com.example.PEP1MINGESO.repositories.EstudiantesRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        int arancel = 1500000;
        int numero_Cuotas = Integer.parseInt(numeroCuota);
        if (numero_Cuotas == 1){
            int valorCuotas = 1500000/2;
            CuotaEntity cuota = new CuotaEntity();
            cuota.setNumero_cuota(1);
            cuota.setRut_cuota(rut);
            cuota.setMonto(valorCuotas);
            cuota.setPagada(false);
            cuotaRepository.save(cuota);
        }else{
            Optional<EstudiantesEntity> estudiante = estudiantesRepository.findById(rut);
            EstudiantesEntity estudiante1 = estudiante.get();
            System.out.println(estudiante1);
            double arancelDescuento = estudiantesService.calcularArancel(estudiante1);
            int valorCuotas = (int)arancelDescuento/numero_Cuotas;
            for (int i = 1; i<= numero_Cuotas; i++){
                CuotaEntity cuota = new CuotaEntity();
                cuota.setNumero_cuota(i);
                cuota.setRut_cuota(rut);
                cuota.setMonto(valorCuotas);
                cuota.setPagada(false);
                cuotaRepository.save(cuota);
            }
        }
    }
}
