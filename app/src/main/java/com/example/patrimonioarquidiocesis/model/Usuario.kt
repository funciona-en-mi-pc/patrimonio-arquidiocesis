package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey(autoGenerate = true) val usuarioId: Int = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String,
    val institucion: String = ""
)