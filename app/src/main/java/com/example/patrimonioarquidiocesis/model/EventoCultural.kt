package com.example.patrimonioarquidiocesis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventoCultural")
data class EventoCultural(
    @PrimaryKey(autoGenerate = true) val eventoId: Int = 0,
    val temploId: Int,
    val nombreEvento: String,
    val tipoEvento: String,
    val fechaHora: String,
    val descripcion: String,
)