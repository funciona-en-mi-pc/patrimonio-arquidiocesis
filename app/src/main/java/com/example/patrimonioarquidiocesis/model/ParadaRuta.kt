package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paradaRuta")
data class ParadaRuta(
    @PrimaryKey(autoGenerate = true) val paradaId: Int = 0,
    val rutaId: Int,
    val temploId: Int,
    val orden: Int,
    val tiempoEstimadoTraslado: Double
)
