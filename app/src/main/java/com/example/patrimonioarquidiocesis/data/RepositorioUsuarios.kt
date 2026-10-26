package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.dao.ConfiguracionDao
import com.example.patrimonioarquidiocesis.dao.UsuarioDao
import com.example.patrimonioarquidiocesis.model.Usuario

class RepositorioUsuarios(
    private val usuarioDao: UsuarioDao,
    configuracionDao: ConfiguracionDao
) {
    private val configuracion = RepositorioConfiguracion(configuracionDao)

    // null si el correo no existe o la clave está mal; si es correcto guarda la sesión
    suspend fun iniciarSesion(email: String, password: String): Usuario? {
        val usuario = usuarioDao.buscarPorEmail(email.trim().lowercase())
        if (usuario == null || usuario.password != password) return null
        configuracion.guardarSesion(usuario.usuarioId)
        return usuario
    }

    suspend fun usuarioActual(): Usuario? {
        val id = configuracion.leerSesion() ?: return null
        return usuarioDao.buscarPorId(id)
    }

    suspend fun cerrarSesion() {
        configuracion.borrarSesion()
    }
}