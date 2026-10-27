package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignacionGuia")
data class AsignacionGuia(
    @PrimaryKey(autoGenerate = true) val asignacionId: Int = 0,
    val rutaId: Int,
    val guiaId: Int,
    val fechaAsignacion: String,
    val estadoAsignacion: String = Constantes.ASIGNACION_PENDIENTE
)
