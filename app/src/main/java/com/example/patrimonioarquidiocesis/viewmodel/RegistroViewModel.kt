package com.example.patrimonioarquidiocesis.viewmodel

import androidx.compose.runtime.getValue //Por el By recuerda el set
import androidx.compose.runtime.mutableStateOf //MutableStateOf
import androidx.compose.runtime.setValue //Por el By recuerda el get
import androidx.lifecycle.ViewModel


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

}