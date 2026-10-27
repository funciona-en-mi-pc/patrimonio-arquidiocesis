package com.patrimonio.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "salida_pedagogica")
public class SalidaPedagogica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salidaId;

    private Integer colegioId;

    private Integer rutaId;

    private String fechaSalida;

    private Integer cantidadEstudiantes;

    @Column(length = 1000)
    private String observaciones;

    private String estado;
}
