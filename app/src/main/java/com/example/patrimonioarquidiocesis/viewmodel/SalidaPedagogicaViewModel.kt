package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioRutas
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import com.example.patrimonioarquidiocesis.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalidaPedagogicaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())
    private val repoRutas = RepositorioRutas(db.rutaDao(), db.paradaDao(), db.salidaDao(), db.asignacionDao())

    // El colegio con la sesión iniciada (null = cargando)
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    // Solo las rutas confirmadas del colegio se pueden elegir
    private val _rutas = MutableStateFlow<List<RutaPatrimonial>>(emptyList())
    val rutas: StateFlow<List<RutaPatrimonial>> = _rutas

    // Campos del formulario
    var rutaId by mutableStateOf(0)
        private set
    var fecha by mutableStateOf("")
        private set
    var cantidad by mutableStateOf("")
        private set
    var observaciones by mutableStateOf("")
        private set
    var mensaje by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            val actual = repoUsuarios.usuarioActual() ?: return@launch
            _usuario.value = actual
            repoRutas.listarRutasDe(actual.usuarioId).collect { lista ->
                _rutas.value = lista.filter { it.estado == Constantes.RUTA_CONFIRMADA }
                // Por defecto queda elegida la primera ruta
                if (rutaId == 0 && _rutas.value.isNotEmpty()) {
                    rutaId = _rutas.value.first().rutaId
                }
            }
        }
    }

    fun cambiarRuta(nueva: Int) { rutaId = nueva }
    fun cambiarFecha(nueva: String) { fecha = nueva }
    fun cambiarCantidad(nueva: String) { cantidad = nueva }
    fun cambiarObservaciones(nuevas: String) { observaciones = nuevas }

    fun enviar() {
        val colegio = _usuario.value ?: return
        val estudiantes = cantidad.toIntOrNull()

        if (rutaId == 0) {
            mensaje = "Primero confirma una ruta en \"Arma tu ruta\""
            return
        }
        if (fecha.length != 10) {
            mensaje = "Usa el formato AAAA-MM-DD para la fecha"
            return
        }
        if (estudiantes == null || estudiantes <= 0) {
            mensaje = "Ingresa una cantidad de estudiantes válida"
            return
        }
        viewModelScope.launch {
            repoRutas.crearSalidaPedagogica(colegio.usuarioId, rutaId, fecha, estudiantes, observaciones.trim())
            fecha = ""
            cantidad = ""
            observaciones = ""
            mensaje = "Solicitud enviada"
        }
    }
}
