package com.example.patrimonioarquidiocesis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FichaTemploViewModel : ViewModel() {

    private val _templo = MutableStateFlow<Templo?>(null)
    val templo: StateFlow<Templo?> = _templo

    private val _estado = MutableStateFlow(Constantes.ESTADO_CERRADO)
    val estado: StateFlow<String> = _estado

    fun cargar(temploId: Int) {
        viewModelScope.launch {
            _estado.value = RepositorioTemplos.estadoActualDe(temploId)
            _templo.value = RepositorioTemplos.obtenerTemplo(temploId)

        }

    }


    //FIN//
}