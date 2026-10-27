package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.dao.AsignacionDao
import com.example.patrimonioarquidiocesis.dao.ParadaDao
import com.example.patrimonioarquidiocesis.dao.RutaDao
import com.example.patrimonioarquidiocesis.dao.SalidaDao
import com.example.patrimonioarquidiocesis.model.AsignacionGuia
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.ParadaRuta
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import com.example.patrimonioarquidiocesis.model.SalidaPedagogica
import kotlinx.coroutines.flow.Flow

class RepositorioRutas(
    private val rutaDao: RutaDao,
    private val paradaDao: ParadaDao,
    private val salidaDao: SalidaDao,
    private val asignacionDao: AsignacionDao
) {
    // ---- Rutas ----
    fun listarRutasDe(usuarioId: Int): Flow<List<RutaPatrimonial>> {
        return rutaDao.listarDeUsuario(usuarioId)
    }

    suspend fun leerTodasLasRutas(): List<RutaPatrimonial> {
        return rutaDao.leerTodas()
    }

    suspend fun crearRuta(usuarioCreadorId: Int, nombre: String): RutaPatrimonial {
        val nueva = RutaPatrimonial(usuarioCreadorId = usuarioCreadorId, nombreRuta = nombre)
        val id = rutaDao.insertar(nueva).toInt()
        return nueva.copy(rutaId = id)
    }

    // La ruta que el usuario está armando; si no tiene una, se crea
    suspend fun rutaBorradorDe(usuarioId: Int): RutaPatrimonial {
        return rutaDao.buscarPorEstado(usuarioId, Constantes.RUTA_BORRADOR)
            ?: crearRuta(usuarioId, "Mi ruta patrimonial")
    }

    // La usa la Ficha del templo (botón "Agregar a mi ruta")
    suspend fun agregarTemploARutaBorrador(usuarioId: Int, temploId: Int) {
        val ruta = rutaBorradorDe(usuarioId)
        agregarParada(ruta.rutaId, temploId)
    }

    suspend fun obtenerRuta(rutaId: Int): RutaPatrimonial? {
        return rutaDao.buscarPorId(rutaId)
    }

    // U: la ruta pasa de BORRADOR a CONFIRMADA y guarda su tiempo total
    suspend fun confirmarRuta(rutaId: Int) {
        val ruta = rutaDao.buscarPorId(rutaId) ?: return
        val minutos = calcularMinutos(paradaDao.leerDeRuta(rutaId))
        rutaDao.actualizar(ruta.copy(estado = Constantes.RUTA_CONFIRMADA, tiempoEstimadoTotal = minutos.toDouble()))
    }

    // ---- Paradas ----
    fun paradasDe(rutaId: Int): Flow<List<ParadaRuta>> {
        return paradaDao.listarDeRuta(rutaId)
    }

    // Si el templo ya está en la ruta no se repite
    suspend fun agregarParada(rutaId: Int, temploId: Int) {
        val paradas = paradaDao.leerDeRuta(rutaId)
        if (paradas.any { it.temploId == temploId }) return

        // La primera parada es el punto de partida: no tiene traslado
        val traslado = if (paradas.isEmpty()) 0 else Constantes.MINUTOS_TRASLADO
        val orden = (paradas.maxOfOrNull { it.orden } ?: 0) + 1
        paradaDao.insertar(
            ParadaRuta(rutaId = rutaId, temploId = temploId, orden = orden, tiempoEstimadoTraslado = traslado.toDouble())
        )
    }

    // D: quitar una parada de la ruta
    suspend fun quitarParada(paradaId: Int) {
        paradaDao.eliminar(paradaId)
    }

    // shortcut: tiempos fijos por visita y traslado; en la etapa 3 se pueden calcular con latitud/longitud
    // Cada templo es una visita, y entre dos templos hay un traslado (la primera parada no tiene)
    fun calcularMinutos(paradas: List<ParadaRuta>): Int {
        if (paradas.isEmpty()) return 0
        return paradas.size * Constantes.MINUTOS_VISITA + (paradas.size - 1) * Constantes.MINUTOS_TRASLADO
    }

    // ---- Salida pedagógica ----
    suspend fun crearSalidaPedagogica(colegioId: Int, rutaId: Int, fecha: String, cantidadEstudiantes: Int, observaciones: String) {
        val salida = SalidaPedagogica(
            colegioId = colegioId,
            rutaId = rutaId,
            fechaSalida = fecha,
            cantidadEstudiantes = cantidadEstudiantes,
            observaciones = observaciones
        )
        salidaDao.insertar(salida)
    }

    suspend fun leerSalidas(): List<SalidaPedagogica> {
        return salidaDao.leerTodas()
    }

    // ---- Asignaciones del guía ----
    fun asignacionesDe(guiaId: Int): Flow<List<AsignacionGuia>> {
        return asignacionDao.listarDeGuia(guiaId)
    }

    suspend fun asignacionDeRuta(rutaId: Int): AsignacionGuia? {
        return asignacionDao.buscarDeRuta(rutaId)
    }

    // U: el guía acepta (CONFIRMADA) o rechaza (RECHAZADA)
    suspend fun responderAsignacion(asignacionId: Int, aceptar: Boolean) {
        val estado = if (aceptar) Constantes.ASIGNACION_CONFIRMADA else Constantes.ASIGNACION_RECHAZADA
        asignacionDao.cambiarEstado(asignacionId, estado)
    }
}
