package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.Usuario
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repositorio = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    fun cambiarEmail(nuevo: String) { email = nuevo }
    fun cambiarPassword(nuevo: String) { password = nuevo }

    // Si el login es correcto llama a onExito con el usuario; si no, deja un mensaje de error
    fun iniciarSesion(onExito: (Usuario) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            mensaje = "Complete correo y contraseña"
            return
        }
        viewModelScope.launch {
            val usuario = repositorio.iniciarSesion(email, password)
            if (usuario != null) {
                mensaje = ""
                onExito(usuario)
            } else {
                mensaje = "Correo o contraseña incorrectos"
            }
        }
    }
}