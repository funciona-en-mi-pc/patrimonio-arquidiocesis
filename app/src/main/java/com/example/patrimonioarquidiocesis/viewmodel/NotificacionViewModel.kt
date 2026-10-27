package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioEventos
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.model.AvisoNotificacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificacionesViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repositorio = RepositorioEventos(db.eventoDao(), db.avisoDao())

    // Avisos desde SQLite (null = cargando)
    private val _avisos = MutableStateFlow<List<AvisoNotificacion>?>(null)
    val avisos: StateFlow<List<AvisoNotificacion>?> = _avisos

    // temploId -> nombre, para el título de cada aviso
    private val _nombresTemplos = MutableStateFlow<Map<Int, String>>(emptyMap())
    val nombresTemplos: StateFlow<Map<Int, String>> = _nombresTemplos

    init {
        viewModelScope.launch {
            val templos = RepositorioTemplos.listarTemplos()
            val mapa = mutableMapOf<Int, String>()
            for (templo in templos) {
                mapa[templo.temploId] = templo.nombreOficial
            }
            _nombresTemplos.value = mapa

            repositorio.listarAvisos().collect { lista ->
                _avisos.value = lista
            }
        }
    }
}