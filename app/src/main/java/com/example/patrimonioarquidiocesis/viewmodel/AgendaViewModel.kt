package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioEventos
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.model.EventoCultural
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgendaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repositorio = RepositorioEventos(db.eventoDao(), db.avisoDao())

    // Eventos desde SQLite (null = cargando)
    private val _eventos = MutableStateFlow<List<EventoCultural>?>(null)
    val eventos: StateFlow<List<EventoCultural>?> = _eventos

    // temploId -> nombre del templo, para mostrar "Catedral de Santiago" en cada evento
    private val _nombresTemplos = MutableStateFlow<Map<Int, String>>(emptyMap())
    val nombresTemplos: StateFlow<Map<Int, String>> = _nombresTemplos

    // Filtro elegido: "TODOS" o un tipo de evento
    private val _filtro = MutableStateFlow("TODOS")
    val filtro: StateFlow<String> = _filtro

    init {
        viewModelScope.launch {
            val templos = RepositorioTemplos.listarTemplos()
            val mapa = mutableMapOf<Int, String>()
            for (templo in templos) {
                mapa[templo.temploId] = templo.nombreOficial
            }
            _nombresTemplos.value = mapa

            // Queda escuchando la tabla: si se crea un evento, llega solo
            repositorio.listarEventos().collect { lista ->
                _eventos.value = lista
            }
        }
    }

    fun cambiarFiltro(nuevoFiltro: String) {
        _filtro.value = nuevoFiltro
    }
}