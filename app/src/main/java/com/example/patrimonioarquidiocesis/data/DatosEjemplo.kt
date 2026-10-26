package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.model.*

object DatosEjemplo {

    val usuarios = listOf(
        Usuario(usuarioId = 1, nombre = "María Pérez", email = "maria@correo.cl", password = "maria123", rol = Constantes.ROL_TURISTA),
        Usuario(usuarioId = 2, nombre = "Colegio San Ignacio", email = "colegio@correo.cl", password = "colegio123", rol = Constantes.ROL_COLEGIO, institucion = "Colegio San Ignacio"),
        Usuario(usuarioId = 3, nombre = "Carlos M.", email = "carlos@correo.cl", password = "carlos123", rol = Constantes.ROL_GUIA),
        Usuario(usuarioId = 4, nombre = "Encargado Catedral", email = "encargado@correo.cl", password = "encargado123", rol = Constantes.ROL_ENCARGADO)
    )

    val templos = listOf(
        Templo(temploId = 1, encargadoId = 4, nombreOficial = "Catedral de Santiago", direccion = "Plaza de Armas s/n, Santiago Centro", latitud = -33.4378, longitud = -70.6504, descripcionHistorica = "Principal templo de la arquidiócesis, testigo de más de dos siglos de historia."),
        Templo(temploId = 2, encargadoId = 4, nombreOficial = "Basílica del Carmen", direccion = "Maipú", latitud = -33.5100, longitud = -70.7600, descripcionHistorica = "Templo votivo levantado tras la independencia."),
        Templo(temploId = 3, encargadoId = 4, nombreOficial = "Iglesia San Francisco", direccion = "Alameda, Santiago", latitud = -33.4450, longitud = -70.6460, descripcionHistorica = "Uno de los edificios más antiguos de la ciudad.")
    )

    val horarios = listOf(
        DisponibilidadHoraria(disponibilidadId = 1, temploId = 1, diaSemana = "LUNES", horaApertura = "09:00", horaCierre = "19:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 2, temploId = 1, diaSemana = "SABADO", horaApertura = "10:00", horaCierre = "20:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 3, temploId = 1, diaSemana = "DOMINGO", horaApertura = "-", horaCierre = "-", esExcepcional = false, estado = Constantes.ESTADO_CERRADO)
    )

    val eventos = listOf(
        EventoCultural(eventoId = 1, temploId = 1, nombreEvento = "Misa solemne patronal", tipoEvento = "LITURGICO", fechaHora = "2026-10-25T18:00", descripcion = "Celebración patronal en la Catedral."),
        EventoCultural(eventoId = 2, temploId = 3, nombreEvento = "Concierto de música sacra", tipoEvento = "CONCIERTO", fechaHora = "2026-10-28T20:00", descripcion = "Coro y órgano en San Francisco."),
        EventoCultural(eventoId = 3, temploId = 2, nombreEvento = "Exposición de arte colonial", tipoEvento = "EXPOSICION", fechaHora = "2026-11-02T10:00", descripcion = "Muestra de pintura colonial.")
    )
}