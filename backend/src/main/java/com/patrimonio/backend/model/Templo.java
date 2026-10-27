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
@Table(name = "templo")
public class Templo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer temploId;

    private Integer encargadoId;

    private String nombreOficial;

    private String direccion;

    private Double latitud;

    private Double longitud;

    @Column(length = 1000)
    private String descripcionHistorica;
}
