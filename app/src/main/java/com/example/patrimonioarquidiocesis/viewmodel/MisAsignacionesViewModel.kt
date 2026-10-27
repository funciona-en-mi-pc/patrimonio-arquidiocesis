package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioRutas
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.AsignacionGuia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MisAsignacionesViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())
    private val repoRutas = RepositorioRutas(db.rutaDao(), db.paradaDao(), db.salidaDao(), db.asignacionDao())

    // Asignaciones del guía desde SQLite (null = cargando)
    private val _asignaciones = MutableStateFlow<List<AsignacionGuia>?>(null)
    val asignaciones: StateFlow<List<AsignacionGuia>?> = _asignaciones

    // rutaId -> nombre de la ruta, para el título de cada tarjeta
    private val _nombresRutas = MutableStateFlow<Map<Int, String>>(emptyMap())
    val nombresRutas: StateFlow<Map<Int, String>> = _nombresRutas

    // rutaId -> quién va ("Colegio San Ignacio · 32 estudiantes")
    private val _detalles = MutableStateFlow<Map<Int, String>>(emptyMap())
    val detalles: StateFlow<Map<Int, String>> = _detalles

    init {
        viewModelScope.launch {
            val guia = repoUsuarios.usuarioActual() ?: return@launch

            // Si una ruta tiene varias salidas, queda la última (se van sobrescribiendo)
            val estudiantes = mutableMapOf<Int, Int>()
            for (salida in repoRutas.leerSalidas()) {
                estudiantes[salida.rutaId] = salida.cantidadEstudiantes
            }

            val nombres = mutableMapOf<Int, String>()
            val detalles = mutableMapOf<Int, String>()
            for (ruta in repoRutas.leerTodasLasRutas()) {
                nombres[ruta.rutaId] = ruta.nombreRuta
                val creador = repoUsuarios.obtenerUsuario(ruta.usuarioCreadorId)?.nombre ?: ""
                val cantidad = estudiantes[ruta.rutaId]
                detalles[ruta.rutaId] = if (cantidad == null) creador else "$creador · $cantidad estudiantes"
            }
            _nombresRutas.value = nombres
            _detalles.value = detalles

            repoRutas.asignacionesDe(guia.usuarioId).collect { lista ->
                _asignaciones.value = lista
            }
        }
    }

    fun responder(asignacionId: Int, aceptar: Boolean) {
        viewModelScope.launch {
            repoRutas.responderAsignacion(asignacionId, aceptar)
        }
    }
}
