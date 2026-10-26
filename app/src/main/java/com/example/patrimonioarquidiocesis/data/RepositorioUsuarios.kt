package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.dao.ConfiguracionDao
import com.example.patrimonioarquidiocesis.dao.UsuarioDao
import com.example.patrimonioarquidiocesis.model.Constantes
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

    suspend fun registrarUsuario(nombre: String, email: String, password: String, rol: String, institucion: String): Usuario? {
        val emailLimpio = email.trim().lowercase()
        if (rol == Constantes.ROL_ENCARGADO) return null
        if (usuarioDao.buscarPorEmail(emailLimpio) != null) return null

        val nuevo = Usuario(nombre = nombre.trim(), email = emailLimpio, password = password, rol = rol, institucion = institucion)
        val id = usuarioDao.insertar(nuevo).toInt()
        configuracion.guardarSesion(id)
        return nuevo.copy(usuarioId = id)
    }
}