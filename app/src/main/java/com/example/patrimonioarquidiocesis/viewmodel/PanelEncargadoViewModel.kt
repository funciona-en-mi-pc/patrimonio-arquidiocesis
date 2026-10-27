package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioEventos
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.EventoCultural
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PanelEncargadoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoEventos = RepositorioEventos(db.eventoDao(), db.avisoDao())
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())

    // Templo que administra el encargado logueado (null = cargando)
    private val _templo = MutableStateFlow<Templo?>(null)
    val templo: StateFlow<Templo?> = _templo

    private val _estado = MutableStateFlow(Constantes.ESTADO_CERRADO)
    val estado: StateFlow<String> = _estado

    // Eventos de su templo, desde SQLite
    private val _eventos = MutableStateFlow<List<EventoCultural>>(emptyList())
    val eventos: StateFlow<List<EventoCultural>> = _eventos

    // Formulario "Publicar aviso" (igual que el RegistroViewModel de Javier)
    var mensajeAviso by mutableStateOf("")
        private set
    var confirmacion by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            val usuario = repoUsuarios.usuarioActual()
            val templos = RepositorioTemplos.listarTemplos()
            val miTemplo = templos.find { it.encargadoId == usuario?.usuarioId } ?: templos.first()

            _estado.value = RepositorioTemplos.estadoActualDe(miTemplo.temploId)
            _templo.value = miTemplo

            repoEventos.eventosDe(miTemplo.temploId).collect { lista ->
                _eventos.value = lista
            }
        }
    }

    fun cambiarMensaje(nuevoMensaje: String) {
        mensajeAviso = nuevoMensaje
        confirmacion = ""
    }

    fun publicarAviso() {
        val temploActual = _templo.value ?: return
        if (mensajeAviso.isBlank()) {
            confirmacion = "Escribe un mensaje antes de publicar"
            return
        }
        viewModelScope.launch {
            repoEventos.publicarAviso(temploActual.temploId, mensajeAviso.trim())
            mensajeAviso = ""
            confirmacion = "Aviso publicado"
        }
    }

    fun cerrarSesion(alTerminar: () -> Unit) {
        viewModelScope.launch {
            repoUsuarios.cerrarSesion()
            alTerminar()
        }
    }
}