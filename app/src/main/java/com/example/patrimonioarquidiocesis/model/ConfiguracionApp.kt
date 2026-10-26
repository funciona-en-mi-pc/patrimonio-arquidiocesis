package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuracion")
data class ConfiguracionApp(
    @PrimaryKey val id: Int = 1,
    val sesionUsuarioId: Int? = null,
    val temaOscuro: Boolean = false
)
