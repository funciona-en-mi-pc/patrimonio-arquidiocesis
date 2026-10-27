package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import com.example.patrimonioarquidiocesis.model.Constantes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioEventos
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import kotlinx.coroutines.launch

class NuevoEventoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoEventos = RepositorioEventos(db.eventoDao(), db.avisoDao())
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())

    // Campos del formulario
    var nombre by mutableStateOf("")
        private set
    var tipo by mutableStateOf(Constantes.EVENTO_CONCIERTO)
        private set
    var fecha by mutableStateOf("")
        private set
    var hora by mutableStateOf("")
        private set
    var descripcion by mutableStateOf("")
        private set
    var mensaje by mutableStateOf("")
        private set

    fun cambiarNombre(nuevo: String) { nombre = nuevo }
    fun cambiarTipo(nuevo: String) { tipo = nuevo }
    fun cambiarFecha(nueva: String) { fecha = nueva }
    fun cambiarHora(nueva: String) { hora = nueva }
    fun cambiarDescripcion(nueva: String) { descripcion = nueva }

    fun publicar(alTerminar: () -> Unit) {
        if (nombre.isBlank() || fecha.isBlank() || hora.isBlank()) {
            mensaje = "Completa nombre, fecha y hora"
            return
        }
        if (fecha.length != 10 || hora.length != 5) {
            mensaje = "Usa el formato AAAA-MM-DD para la fecha y HH:MM para la hora"
            return
        }
        viewModelScope.launch {
            val usuario = repoUsuarios.usuarioActual()
            val templos = RepositorioTemplos.listarTemplos()
            val miTemplo = templos.find { it.encargadoId == usuario?.usuarioId } ?: templos.first()

            repoEventos.crearEvento(
                temploId = miTemplo.temploId,
                nombre = nombre.trim(),
                tipo = tipo,
                fechaHora = "${fecha}T${hora}",
                descripcion = descripcion.trim()
            )
            alTerminar()
        }
    }
}