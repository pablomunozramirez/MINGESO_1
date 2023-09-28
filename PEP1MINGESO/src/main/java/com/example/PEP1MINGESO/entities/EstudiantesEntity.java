package com.example.PEP1MINGESO.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudiantesEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String rut;

    private String nombre;
    private String apellidos;
    private Date fecha_nacimiento;
    private String tipo_colegio;
    private String nombre_colegio;
    private int anio_egreso;
}
