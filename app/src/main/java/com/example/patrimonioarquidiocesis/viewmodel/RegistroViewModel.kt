package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.Usuario
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repositorio = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())

    var nombre by mutableStateOf("")
        private set
    var mail by mutableStateOf("")
        private set
    var pass by mutableStateOf("")
        private set
    var tipoParticipacion by mutableStateOf(Constantes.ROL_TURISTA)   // se guarda la constante, no el texto
        private set
    var institucion by mutableStateOf("")
        private set
    var mensaje by mutableStateOf("")
        private set

    fun cambiarNombre(nuevo: String) { nombre = nuevo }
    fun cambiarMail(nuevo: String) { mail = nuevo }
    fun cambiarPass(nuevo: String) { pass = nuevo }
    fun cambiarTipoParticipacion(nuevo: String) { tipoParticipacion = nuevo }
    fun cambiarInstitucion(nuevo: String) { institucion = nuevo }

    fun crearCuenta(onExito: (Usuario) -> Unit) {
        if (nombre.isBlank() || mail.isBlank() || pass.isBlank()) {
            mensaje = "Por favor completa todos los campos"
            return
        }
        if (!mail.contains("@")) {
            mensaje = "El correo no es válido"
            return
        }
        if (tipoParticipacion == Constantes.ROL_COLEGIO && institucion.isBlank()) {
            mensaje = "Indica el nombre de la institución"
            return
        }
        viewModelScope.launch {
            val usuario =
                repositorio.registrarUsuario(nombre, mail, pass, tipoParticipacion, institucion)
            if (usuario != null) {
                mensaje = ""
                onExito(usuario)
            } else {
                mensaje = "Ese correo ya está registrado"
            }
        }
    }
}