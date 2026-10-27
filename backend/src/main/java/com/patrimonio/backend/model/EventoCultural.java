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
@Table(name = "evento_cultural")
public class EventoCultural {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventoId;

    private Integer temploId;

    private String nombreEvento;

    private String tipoEvento;

    private String fechaHora;

    @Column(length = 1000)
    private String descripcion;
}
