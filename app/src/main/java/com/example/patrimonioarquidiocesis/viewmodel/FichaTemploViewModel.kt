package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.RepositorioRutas
import com.example.patrimonioarquidiocesis.data.RepositorioTemplos
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FichaTemploViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repoUsuarios = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())
    private val repoRutas = RepositorioRutas(db.rutaDao(), db.paradaDao(), db.salidaDao(), db.asignacionDao())

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

    // Agrega el templo a la ruta en BORRADOR del usuario (RepositorioRutas de Javier)
    fun agregarAMiRuta(temploId: Int, alTerminar: () -> Unit) {
        viewModelScope.launch {
            val usuario = repoUsuarios.usuarioActual() ?: return@launch
            repoRutas.agregarTemploARutaBorrador(usuario.usuarioId, temploId)
            alTerminar()
        }
    }


    //FIN//
}