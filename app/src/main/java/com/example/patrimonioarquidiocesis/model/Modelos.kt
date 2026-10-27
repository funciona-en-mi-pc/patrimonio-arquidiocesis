package com.example.patrimonioarquidiocesis.model

data class Templo(
    val temploId: Int,
    val encargadoId: Int,
    val nombreOficial: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double,
    val descripcionHistorica: String
)

data class DisponibilidadHoraria(
    val disponibilidadId: Int,
    val temploId: Int,
    val diaSemana: String,
    val horaApertura: String,
    val horaCierre: String,
    val esExcepcional: Boolean,
    val estado: String
)

data class GaleriaMultimedia(
    val recursoId: Int,
    val temploId: Int,
    val tipoRecurso: String,
    val urlRecurso: String
)
