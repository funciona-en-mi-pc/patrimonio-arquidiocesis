package com.example.patrimonioarquidiocesis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InicioViewModel : ViewModel() {

    // Lista de templos (null = todavía cargando)
    private val _templos = MutableStateFlow<List<Templo>?>(null)
    val templos: StateFlow<List<Templo>?> = _templos

    // Estado de cada templo: temploId -> "ABIERTO" / "CERRADO" / "EN_CELEBRACION"
    private val _estados = MutableStateFlow<Map<Int, String>>(emptyMap())
    val estados: StateFlow<Map<Int, String>> = _estados

    init {
        cargarTemplos()
    }

    fun cargarTemplos() {
        viewModelScope.launch {
            val lista = RepositorioTemplos.listarTemplos()

            val mapa = mutableMapOf<Int, String>()
            for (templo in lista) {
                mapa[templo.temploId] = RepositorioTemplos.estadoActualDe(templo.temploId)
            }

            _estados.value = mapa
            _templos.value = lista
        }
    }
}