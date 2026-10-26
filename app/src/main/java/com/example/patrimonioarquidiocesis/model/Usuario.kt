package com.example.patrimonioarquidiocesis.model

data class Usuario (
    val usuarioId: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String,
    val institucion: String = ""
)