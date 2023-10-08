package com.example.PEP1MINGESO.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_cuota;
    private int numero_cuota;
    private int monto;
    private boolean pagada;

    @Column(name = "rut_cuota")
    private String rutCuota;
    @Column(name = "fecha_cuota")
    private LocalDate fechaCuota;
}
