package com.example.patrimonioarquidiocesis.model

object Constantes{
    //Roles de usuarios
    const val ROL_TURISTA = "TURISTA"
    const val ROL_COLEGIO = "COLEGIO"
    const val  ROL_GUIA = "GUIA"
    const val ROL_ENCARGADO = "ENCARGADO"

    //Roles de Templo/ Horarios
    const val ESTADO_ABIERTO = "ABIERTO"
    const val ESTADO_CERRADO = "CERRADO"
    const val ESTADO_EN_CELEBRACION = "EN_CELEBRACION"

    const val EVENTO_LITURGICO = "LITURGICO"
    const val EVENTO_CONCIERTO = "CONCIERTO"
    const val EVENTO_EXPOSICION = "EXPOSICION"

    //Estados de ruta
    const val RUTA_BORRADOR = "BORRADOR"
    const val RUTA_CONFIRMADA = "CONFIRMADA"

    //Estados de asignación del guía
    const val ASIGNACION_PENDIENTE = "PENDIENTE"
    const val ASIGNACION_CONFIRMADA = "CONFIRMADA"
    const val ASIGNACION_RECHAZADA = "RECHAZADA"

    //Estados de salida pedagógica
    const val SALIDA_PENDIENTE = "PENDIENTE"

    //Tiempos para calcular la duración de una ruta (en minutos)
    const val MINUTOS_VISITA = 13
    const val MINUTOS_TRASLADO = 12

}

