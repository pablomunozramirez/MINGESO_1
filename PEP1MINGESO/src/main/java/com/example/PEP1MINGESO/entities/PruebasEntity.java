package com.example.PEP1MINGESO.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "pruebas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PruebasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_prueba;

    private Date fecha_examen;
    private int puntaje;
    private String rut_prueba;
}