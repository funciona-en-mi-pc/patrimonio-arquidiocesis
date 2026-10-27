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
@Table(name = "aviso_notificacion")
public class AvisoNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer avisoId;

    private Integer temploId;

    private String tipoAviso;

    @Column(length = 1000)
    private String mensaje;

    private String fechaHora;

    private Boolean sincronizado;
}
