package com.example.PEP1MINGESO.services;

import com.example.PEP1MINGESO.entities.EstudiantesEntity;
import com.example.PEP1MINGESO.entities.PruebasEntity;
import com.example.PEP1MINGESO.repositories.PruebasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PruebasService {
    @Autowired
    PruebasRepository pruebasRepository;

    private final Logger logg = LoggerFactory.getLogger(PruebasService.class);

    public ArrayList<PruebasEntity> obtenerData() {
        return (ArrayList<PruebasEntity>) pruebasRepository.findAll();
    }

    public String guardar(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    PruebasEntity prueba = new PruebasEntity();
                    prueba.setRutPrueba(parts[0]);
                    prueba.setFecha_examen(LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    prueba.setPuntaje(Integer.parseInt(parts[2]));
                    System.out.println(prueba);
                    pruebasRepository.save(prueba);
                } else {
                    logg.warn("Formato incorrecto en línea CSV: {}", line);
                }
            }

            return "Datos cargados con éxito";
        } catch (IOException e) {
            logg.error("Error al leer el archivo CSV", e);
            return "Error al leer el archivo CSV";
        }
    }
    public int calcularPromedioPruebas(String rut){
        ArrayList<PruebasEntity> pruebas = pruebasRepository.findByRutPrueba(rut);
        int puntaje = 0;
        int numeroExamenes = pruebas.size();
        for (PruebasEntity prueba : pruebas) {
            int aux = prueba.getPuntaje();
            puntaje = puntaje + aux;
        }
        if (puntaje != 0){
            return puntaje /numeroExamenes;
        }
        return 0;
    }
}
