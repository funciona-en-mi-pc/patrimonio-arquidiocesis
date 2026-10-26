package com.example.patrimonioarquidiocesis.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios

class LoginViewModel : ViewModel(){

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    fun cambiarEmail(nuevo : String){ email = nuevo}
    fun cambiarPassword(nuevo: String) {password = nuevo}

    //Devuelve true si entro bien, si no, deja un mensaje de error

    fun iniciarSesion(): Boolean{
        if(email.isBlank() || password.isBlank()){
            mensaje = "Complete correo y contraseña"
            return false
        }
        val usuario = RepositorioUsuarios.iniciarSesion(email, password)
        return if (usuario != null){
            mensaje = ""
            true
        }else{
            mensaje = "correo o contraseña incorrectos"
            false
        }
    }
}