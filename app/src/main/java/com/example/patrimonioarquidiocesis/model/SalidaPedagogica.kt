package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salidaPedagogica")
data class SalidaPedagogica(
    @PrimaryKey(autoGenerate = true) val salidaId: Int = 0,
    val colegioId: Int,
    val rutaId: Int,
    val fechaSalida: String,
    val cantidadEstudiantes: Int,
    val observaciones: String = "",
    val estado: String = Constantes.SALIDA_PENDIENTE
)
