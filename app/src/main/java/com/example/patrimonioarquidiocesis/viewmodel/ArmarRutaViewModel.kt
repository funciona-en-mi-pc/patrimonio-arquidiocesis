package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioRutas
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.ParadaRuta
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArmarRutaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())
    private val repoRutas = RepositorioRutas(db.rutaDao(), db.paradaDao(), db.salidaDao(), db.asignacionDao())

    // La ruta en BORRADOR del usuario (null = cargando)
    private val _ruta = MutableStateFlow<RutaPatrimonial?>(null)
    val ruta: StateFlow<RutaPatrimonial?> = _ruta

    private val _paradas = MutableStateFlow<List<ParadaRuta>>(emptyList())
    val paradas: StateFlow<List<ParadaRuta>> = _paradas

    // Catálogo de templos, para mostrar nombres y poder agregar
    private val _templos = MutableStateFlow<List<Templo>>(emptyList())
    val templos: StateFlow<List<Templo>> = _templos

    var mostrarTemplos by mutableStateOf(false)
        private set
    var mensaje by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            _templos.value = RepositorioTemplos.listarTemplos()
            val usuario = repoUsuarios.usuarioActual() ?: return@launch
            val borrador = repoRutas.rutaBorradorDe(usuario.usuarioId)
            _ruta.value = borrador
            repoRutas.paradasDe(borrador.rutaId).collect { lista ->
                _paradas.value = lista
            }
        }
    }

    fun alternarTemplos() { mostrarTemplos = !mostrarTemplos }

    fun agregar(temploId: Int) {
        val actual = _ruta.value ?: return
        viewModelScope.launch {
            repoRutas.agregarParada(actual.rutaId, temploId)
            mostrarTemplos = false
            mensaje = ""
        }
    }

    fun quitar(paradaId: Int) {
        viewModelScope.launch {
            repoRutas.quitarParada(paradaId)
        }
    }

    fun minutosTotales(paradas: List<ParadaRuta>): Int {
        return repoRutas.calcularMinutos(paradas)
    }

    // Si se confirma, avisa con el id de la ruta para abrir su detalle
    fun confirmar(alTerminar: (Int) -> Unit) {
        val actual = _ruta.value ?: return
        if (_paradas.value.isEmpty()) {
            mensaje = "Agrega al menos un templo a la ruta"
            return
        }
        viewModelScope.launch {
            repoRutas.confirmarRuta(actual.rutaId)
            alTerminar(actual.rutaId)
        }
    }
}
