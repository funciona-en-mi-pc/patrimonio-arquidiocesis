package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.dao.ConfiguracionDao
import com.example.patrimonioarquidiocesis.model.ConfiguracionApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositorioConfiguracion(private val configuracionDao: ConfiguracionDao) {

    // La primera vez la tabla está vacía: se parte de los valores por defecto
    private suspend fun actual(): ConfiguracionApp {
        return configuracionDao.leer() ?: ConfiguracionApp()
    }

    suspend fun guardarSesion(usuarioId: Int) {
        configuracionDao.guardar(actual().copy(sesionUsuarioId = usuarioId))
    }

    suspend fun leerSesion(): Int? {
        return configuracionDao.leer()?.sesionUsuarioId
    }

    suspend fun borrarSesion() {
        configuracionDao.guardar(actual().copy(sesionUsuarioId = null))
    }

    suspend fun guardarTemaOscuro(v: Boolean) {
        configuracionDao.guardar(actual().copy(temaOscuro = v))
    }

    fun observarTemaOscuro(): Flow<Boolean> {
        return configuracionDao.observar().map { it?.temaOscuro ?: false }
    }
}