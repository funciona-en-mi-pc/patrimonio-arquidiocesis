package com.example.patrimonioarquidiocesis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.patrimonioarquidiocesis.data.AppDatabase
import com.example.patrimonioarquidiocesis.data.DatosEjemplo
import com.example.patrimonioarquidiocesis.data.RepositorioConfiguracion
import com.example.patrimonioarquidiocesis.data.RepositorioUsuarios
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.navigation.Rutas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val repositorio = RepositorioUsuarios(db.usuarioDao(), db.configuracionDao())

    private val _rutaInicial = MutableStateFlow<String?>(null)   // null = todavía cargando
    val rutaInicial: StateFlow<String?> = _rutaInicial

    // Tema oscuro guardado en la tabla configuracion (se actualiza solo cuando cambia)
    private val _temaOscuro = MutableStateFlow(false)
    val temaOscuro: StateFlow<Boolean> = _temaOscuro

    init {
        viewModelScope.launch {
            DatosEjemplo.sembrar(db)
            val usuario = repositorio.usuarioActual()
            _rutaInicial.value = when {
                usuario == null -> Rutas.LOGIN
                usuario.rol == Constantes.ROL_ENCARGADO -> Rutas.PANEL_ENCARGADO
                else -> Rutas.INICIO
            }
        }
        viewModelScope.launch {
            RepositorioConfiguracion(db.configuracionDao()).observarTemaOscuro().collect { valor ->
                _temaOscuro.value = valor
            }
        }
    }
}