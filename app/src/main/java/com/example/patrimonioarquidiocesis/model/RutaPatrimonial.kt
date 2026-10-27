package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutaPatrimonial")
data class RutaPatrimonial(
    @PrimaryKey(autoGenerate = true) val rutaId: Int = 0,
    val usuarioCreadorId: Int,
    val nombreRuta: String,
    val tiempoEstimadoTotal: Double = 0.0,
    val estado: String = Constantes.RUTA_BORRADOR
)
