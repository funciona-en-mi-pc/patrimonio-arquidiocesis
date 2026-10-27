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

data class RutaPatrimonial(
    val rutaId: Int,
    val usuarioCreadorId: Int,
    val nombreRuta: String,
    val tiempoEstimadoTotal: Double,
    val estado: String
)

data class ParadaRuta(
    val paradaId: Int,
    val rutaId: Int,
    val temploId: Int,
    val orden: Int,
    val tiempoEstimadoTraslado: Double
)

data class SalidaPedagogica(
    val salidaId: Int,
    val colegioId: Int,
    val rutaId: Int,
    val fechaSalida: String,
    val cantidadEstudiantes: Int,
    val estado: String
)

data class AsignacionGuia(
    val asignacionId: Int,
    val rutaId: Int,
    val guiaId: Int,
    val fechaAsignacion: String,
    val estadoAsignacion: String
)

