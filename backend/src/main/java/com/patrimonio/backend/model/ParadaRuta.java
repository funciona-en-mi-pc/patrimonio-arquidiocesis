package com.patrimonio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "parada_ruta")
public class ParadaRuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paradaId;

    private Integer rutaId;

    private Integer temploId;

    private Integer orden;

    private Double tiempoEstimadoTraslado;
}
