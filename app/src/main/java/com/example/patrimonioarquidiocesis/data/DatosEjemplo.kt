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
        // Catedral de Santiago (temploId = 1)
        DisponibilidadHoraria(disponibilidadId = 1, temploId = 1, diaSemana = "LUNES", horaApertura = "09:00", horaCierre = "19:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 2, temploId = 1, diaSemana = "MARTES", horaApertura = "09:00", horaCierre = "19:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 3, temploId = 1, diaSemana = "MIERCOLES", horaApertura = "09:00", horaCierre = "19:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 4, temploId = 1, diaSemana = "JUEVES", horaApertura = "09:00", horaCierre = "19:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 5, temploId = 1, diaSemana = "VIERNES", horaApertura = "09:00", horaCierre = "19:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 6, temploId = 1, diaSemana = "SABADO", horaApertura = "10:00", horaCierre = "20:00", esExcepcional = false, estado = Constantes.ESTADO_EN_CELEBRACION),
        DisponibilidadHoraria(disponibilidadId = 7, temploId = 1, diaSemana = "DOMINGO", horaApertura = "-", horaCierre = "-", esExcepcional = false, estado = Constantes.ESTADO_CERRADO),
        DisponibilidadHoraria(disponibilidadId = 8, temploId = 1, diaSemana = "DOMINGO", horaApertura = "11:00", horaCierre = "13:00", esExcepcional = true, estado = Constantes.ESTADO_ABIERTO),
        // Basílica del Carmen (temploId = 2)
        DisponibilidadHoraria(disponibilidadId = 9, temploId = 2, diaSemana = "LUNES", horaApertura = "08:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 10, temploId = 2, diaSemana = "MARTES", horaApertura = "08:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 11, temploId = 2, diaSemana = "MIERCOLES", horaApertura = "08:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 12, temploId = 2, diaSemana = "JUEVES", horaApertura = "08:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 13, temploId = 2, diaSemana = "VIERNES", horaApertura = "08:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 14, temploId = 2, diaSemana = "SABADO", horaApertura = "08:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 15, temploId = 2, diaSemana = "DOMINGO", horaApertura = "08:00", horaCierre = "14:00", esExcepcional = false, estado = Constantes.ESTADO_EN_CELEBRACION),
        // Iglesia San Francisco (temploId = 3)
        DisponibilidadHoraria(disponibilidadId = 16, temploId = 3, diaSemana = "LUNES", horaApertura = "-", horaCierre = "-", esExcepcional = false, estado = Constantes.ESTADO_CERRADO),
        DisponibilidadHoraria(disponibilidadId = 17, temploId = 3, diaSemana = "MARTES", horaApertura = "10:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 18, temploId = 3, diaSemana = "MIERCOLES", horaApertura = "10:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 19, temploId = 3, diaSemana = "JUEVES", horaApertura = "10:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 20, temploId = 3, diaSemana = "VIERNES", horaApertura = "10:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 21, temploId = 3, diaSemana = "SABADO", horaApertura = "10:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
        DisponibilidadHoraria(disponibilidadId = 22, temploId = 3, diaSemana = "DOMINGO", horaApertura = "10:00", horaCierre = "18:00", esExcepcional = false, estado = Constantes.ESTADO_ABIERTO),
    )

    val eventos = listOf(
        EventoCultural(eventoId = 1, temploId = 1, nombreEvento = "Misa solemne patronal", tipoEvento = Constantes.EVENTO_LITURGICO, fechaHora = "2026-10-25T18:00", descripcion = "Celebración patronal en la Catedral."),
        EventoCultural(eventoId = 2, temploId = 3, nombreEvento = "Concierto de música sacra", tipoEvento = Constantes.EVENTO_CONCIERTO, fechaHora = "2026-10-28T20:00", descripcion = "Coro y órgano en San Francisco."),
        EventoCultural(eventoId = 3, temploId = 2, nombreEvento = "Exposición de arte colonial", tipoEvento = Constantes.EVENTO_EXPOSICION, fechaHora = "2026-11-02T10:00", descripcion = "Muestra de pintura colonial.")
    )


    val avisos = listOf(
        AvisoNotificacion(avisoId = 1, temploId = 1, tipoAviso = "GENERAL", mensaje = "Apertura especial este domingo de 11:00 a 13:00.", fechaHora = "2026-10-25T09:00", sincronizado = true),
        AvisoNotificacion(avisoId = 2, temploId = 2, tipoAviso = "GENERAL", mensaje = "Misa dominical a las 12:00, se pide silencio durante la celebración.", fechaHora = "2026-10-24T18:00", sincronizado = true),
        AvisoNotificacion(avisoId = 3, temploId = 3, tipoAviso = "GENERAL", mensaje = "El templo permanece cerrado los lunes.", fechaHora = "2026-10-23T12:00", sincronizado = true),
    )

    // Ruta 1: la armó el colegio y Carlos aún no responde. Ruta 2: la armó María y Carlos ya confirmó.
    val rutas = listOf(
        RutaPatrimonial(rutaId = 1, usuarioCreadorId = 2, nombreRuta = "Ruta Centro Histórico", tiempoEstimadoTotal = 38.0, estado = Constantes.RUTA_CONFIRMADA),
        RutaPatrimonial(rutaId = 2, usuarioCreadorId = 1, nombreRuta = "Ruta Basílicas del Sur", tiempoEstimadoTotal = 38.0, estado = Constantes.RUTA_CONFIRMADA)
    )

    val paradas = listOf(
        ParadaRuta(paradaId = 1, rutaId = 1, temploId = 1, orden = 1, tiempoEstimadoTraslado = 0.0),
        ParadaRuta(paradaId = 2, rutaId = 1, temploId = 3, orden = 2, tiempoEstimadoTraslado = 12.0),
        ParadaRuta(paradaId = 3, rutaId = 2, temploId = 1, orden = 1, tiempoEstimadoTraslado = 0.0),
        ParadaRuta(paradaId = 4, rutaId = 2, temploId = 2, orden = 2, tiempoEstimadoTraslado = 12.0)
    )

    val salidas = listOf(
        SalidaPedagogica(salidaId = 1, colegioId = 2, rutaId = 1, fechaSalida = "2026-11-12", cantidadEstudiantes = 32, observaciones = "Solicitamos guía con enfoque histórico.")
    )

    val asignaciones = listOf(
        AsignacionGuia(asignacionId = 1, rutaId = 1, guiaId = 3, fechaAsignacion = "2026-11-12T10:00", estadoAsignacion = Constantes.ASIGNACION_PENDIENTE),
        AsignacionGuia(asignacionId = 2, rutaId = 2, guiaId = 3, fechaAsignacion = "2026-11-21T11:00", estadoAsignacion = Constantes.ASIGNACION_CONFIRMADA)
    )

    suspend fun sembrar(db: AppDatabase) {
        if (db.usuarioDao().contar() == 0) {
            usuarios.forEach { db.usuarioDao().insertar(it) }
        }
        if (db.eventoDao().contar() == 0) {
            eventos.forEach { db.eventoDao().insertar(it) }
        }
        if (db.avisoDao().contar() == 0) {
            avisos.forEach { db.avisoDao().insertar(it) }
        }
        if (db.rutaDao().contar() == 0) {
            rutas.forEach { db.rutaDao().insertar(it) }
            paradas.forEach { db.paradaDao().insertar(it) }
            salidas.forEach { db.salidaDao().insertar(it) }
            asignaciones.forEach { db.asignacionDao().insertar(it) }
        }
    }
}