package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.model.Usuario

object RepositorioUsuarios{
    fun iniciarSesion(email: String, password: String): Usuario? {
        return DatosEjemplo.usuarios.find { usuario ->
            usuario.email.lowercase() == email.lowercase() && usuario.password == password
        }
    }
}