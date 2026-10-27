package com.patrimonio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "disponibilidad_horaria")
public class DisponibilidadHoraria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer disponibilidadId;

    private Integer temploId;

    private String diaSemana;

    private String horaApertura;

    private String horaCierre;

    private Boolean esExcepcional;

    private String estado;
}
