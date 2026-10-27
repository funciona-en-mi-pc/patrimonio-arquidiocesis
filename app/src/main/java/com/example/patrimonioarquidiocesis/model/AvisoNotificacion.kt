package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avisoNotificacion")
data class AvisoNotificacion(
    @PrimaryKey(autoGenerate = true) val avisoId: Int = 0,
    val temploId: Int,
    val tipoAviso: String,
    val mensaje: String,
    val fechaHora: String,
    val sincronizado: Boolean
)