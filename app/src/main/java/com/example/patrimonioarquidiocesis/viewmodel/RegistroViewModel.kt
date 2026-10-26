package com.example.patrimonioarquidiocesis.viewmodel

import androidx.compose.runtime.getValue //Por el By recuerda el set
import androidx.compose.runtime.mutableStateOf //MutableStateOf
import androidx.compose.runtime.setValue //Por el By recuerda el get
import androidx.lifecycle.ViewModel
import com.example.patrimonioarquidiocesis.model.Usuario



class RegistroViewModel : ViewModel() {

    //ESTADO DE LA PANTALLA
    var nombre by mutableStateOf("")
        private set

    var mail by mutableStateOf("")
        private set

    var pass by mutableStateOf("")
        private set

    var tipoParticipacion by mutableStateOf("Turista")
        private set

    var mensaje by mutableStateOf("")
        private set

    fun cambiarNombre(nuevoNombre: String){
        nombre = nuevoNombre
    }

    fun cambiarMail(nuevoMail: String){
        mail = nuevoMail
    }

    fun cambiarPass(nuevoPass: String){
        pass = nuevoPass
    }

    fun cambiarTipoParticipacion (nuevoTipoParticipacion: String){
        tipoParticipacion = nuevoTipoParticipacion
    }


    fun crearCuenta(){
        if (nombre.isBlank() || mail.isBlank() || pass.isBlank()){
            mensaje = "Por favor completar todos los campos"
        } else if (!mail.contains("@")){
            mensaje = "El correo no es valido"
        }else{
            val nuevoUsuario = Usuario(0,nombre, mail, pass, tipoParticipacion)
            mensaje = "Cuenta creada para ${nuevoUsuario.nombre}"
        }
    }

}