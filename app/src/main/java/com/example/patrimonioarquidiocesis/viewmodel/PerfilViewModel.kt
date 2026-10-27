package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioConfiguracion
import com.example.patrimonioarquidiocesis.data.RepositorioRutas
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import com.example.patrimonioarquidiocesis.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())
    private val repoRutas = RepositorioRutas(db.rutaDao(), db.paradaDao(), db.salidaDao(), db.asignacionDao())
    private val configuracion = RepositorioConfiguracion(db.configuracionDao())

    // null = todavía cargando
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _rutas = MutableStateFlow<List<RutaPatrimonial>>(emptyList())
    val rutas: StateFlow<List<RutaPatrimonial>> = _rutas

    private val _temaOscuro = MutableStateFlow(false)
    val temaOscuro: StateFlow<Boolean> = _temaOscuro

    init {
        viewModelScope.launch {
            val actual = repoUsuarios.usuarioActual() ?: return@launch
            _usuario.value = actual
            // Las rutas del usuario se actualizan solas (Flow)
            repoRutas.listarRutasDe(actual.usuarioId).collect { lista ->
                _rutas.value = lista
            }
        }
        viewModelScope.launch {
            configuracion.observarTemaOscuro().collect { valor ->
                _temaOscuro.value = valor
            }
        }
    }

    // Se guarda en SQLite; MainActivity lo observa y cambia el tema de toda la app
    fun cambiarTemaOscuro(valor: Boolean) {
        viewModelScope.launch {
            configuracion.guardarTemaOscuro(valor)
        }
    }

    fun cerrarSesion(alTerminar: () -> Unit) {
        viewModelScope.launch {
            repoUsuarios.cerrarSesion()
            alTerminar()
        }
    }
}
