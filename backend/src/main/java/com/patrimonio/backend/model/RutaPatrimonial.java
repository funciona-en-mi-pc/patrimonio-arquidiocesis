package com.patrimonio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ruta_patrimonial")
public class RutaPatrimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rutaId;

    private Integer usuarioCreadorId;

    private String nombreRuta;

    private Double tiempoEstimadoTotal;

    private String estado;
}
