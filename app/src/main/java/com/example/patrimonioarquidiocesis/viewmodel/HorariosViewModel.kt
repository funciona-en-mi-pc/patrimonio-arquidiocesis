package com.example.patrimonioarquidiocesis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.DisponibilidadHoraria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HorariosViewModel : ViewModel() {

    // Horarios del templo (null = todavía cargando)
    private val _horarios = MutableStateFlow<List<DisponibilidadHoraria>?>(null)
    val horarios: StateFlow<List<DisponibilidadHoraria>?> = _horarios

    // Nombre del templo, para mostrarlo bajo el título
    private val _nombreTemplo = MutableStateFlow("")
    val nombreTemplo: StateFlow<String> = _nombreTemplo

    // Estado de hoy, para el recuadro "Estado actual"
    private val _estado = MutableStateFlow(Constantes.ESTADO_CERRADO)
    val estado: StateFlow<String> = _estado

    fun cargar(temploId: Int) {
        viewModelScope.launch {
            _nombreTemplo.value = RepositorioTemplos.obtenerTemplo(temploId)?.nombreOficial ?: ""
            _estado.value = RepositorioTemplos.estadoActualDe(temploId)
            _horarios.value = RepositorioTemplos.horariosDe(temploId)
        }
    }
}