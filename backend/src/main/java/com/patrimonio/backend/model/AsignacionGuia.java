package com.patrimonio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "asignacion_guia")
public class AsignacionGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer asignacionId;

    private Integer rutaId;

    private Integer guiaId;

    private String fechaAsignacion;

    private String estadoAsignacion;
}
