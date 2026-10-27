package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioRutas
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.AsignacionGuia
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.ParadaRuta
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetalleRutaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())
    private val repoRutas = RepositorioRutas(db.rutaDao(), db.paradaDao(), db.salidaDao(), db.asignacionDao())

    // null = cargando
    private val _ruta = MutableStateFlow<RutaPatrimonial?>(null)
    val ruta: StateFlow<RutaPatrimonial?> = _ruta

    private val _paradas = MutableStateFlow<List<ParadaRuta>>(emptyList())
    val paradas: StateFlow<List<ParadaRuta>> = _paradas

    private val _templos = MutableStateFlow<List<Templo>>(emptyList())
    val templos: StateFlow<List<Templo>> = _templos

    // Guía asignado a la ruta (null = sin guía)
    private val _asignacion = MutableStateFlow<AsignacionGuia?>(null)
    val asignacion: StateFlow<AsignacionGuia?> = _asignacion

    private val _nombreGuia = MutableStateFlow("")
    val nombreGuia: StateFlow<String> = _nombreGuia

    // Solo el COLEGIO ve el botón de salida pedagógica (regla 5)
    private val _esColegio = MutableStateFlow(false)
    val esColegio: StateFlow<Boolean> = _esColegio

    fun cargar(rutaId: Int) {
        viewModelScope.launch {
            _templos.value = RepositorioTemplos.listarTemplos()
            _esColegio.value = repoUsuarios.usuarioActual()?.rol == Constantes.ROL_COLEGIO

            val asignacion = repoRutas.asignacionDeRuta(rutaId)
            _asignacion.value = asignacion
            if (asignacion != null) {
                _nombreGuia.value = repoUsuarios.obtenerUsuario(asignacion.guiaId)?.nombre ?: "Guía"
            }

            _ruta.value = repoRutas.obtenerRuta(rutaId)
            repoRutas.paradasDe(rutaId).collect { lista ->
                _paradas.value = lista
            }
        }
    }

    fun minutosTotales(paradas: List<ParadaRuta>): Int {
        return repoRutas.calcularMinutos(paradas)
    }
}
