package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.dao.AvisoDao
import com.example.patrimonioarquidiocesis.dao.EventoDao
import com.example.patrimonioarquidiocesis.model.AvisoNotificacion
import com.example.patrimonioarquidiocesis.model.EventoCultural
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class RepositorioEventos(
    private val eventoDao: EventoDao,
    private val avisoDao: AvisoDao
) {
    // ---- Eventos ----
    fun listarEventos(): Flow<List<EventoCultural>> {
        return eventoDao.listar()
    }

    fun eventosDe(temploId: Int): Flow<List<EventoCultural>> {
        return eventoDao.listarDeTemplo(temploId)
    }

    suspend fun crearEvento(temploId: Int, nombre: String, tipo: String, fechaHora: String, descripcion: String) {
        val evento = EventoCultural(
            temploId = temploId,
            nombreEvento = nombre,
            tipoEvento = tipo,
            fechaHora = fechaHora,
            descripcion = descripcion
        )
        eventoDao.insertar(evento)
    }

    // ---- Avisos ----
    fun listarAvisos(): Flow<List<AvisoNotificacion>> {
        return avisoDao.listar()
    }

    fun avisosDe(temploId: Int): Flow<List<AvisoNotificacion>> {
        return avisoDao.listarDeTemplo(temploId)
    }

    suspend fun publicarAviso(temploId: Int, mensaje: String) {
        val aviso = AvisoNotificacion(
            temploId = temploId,
            tipoAviso = "GENERAL",
            mensaje = mensaje,
            fechaHora = LocalDateTime.now().toString().take(16),
            sincronizado = false
        )
        avisoDao.insertar(aviso)
    }
}