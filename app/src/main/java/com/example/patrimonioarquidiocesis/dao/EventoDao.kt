package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.EventoCultural
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {

    @Insert
    suspend fun insertar(evento: EventoCultural)

    @Query("SELECT * FROM eventoCultural ORDER BY fechaHora")
    fun listar(): Flow<List<EventoCultural>>

    @Query("SELECT * FROM eventoCultural WHERE temploId = :temploId ORDER BY fechaHora")
    fun listarDeTemplo(temploId: Int): Flow<List<EventoCultural>>

    @Query("SELECT COUNT(*) FROM eventoCultural")
    suspend fun contar(): Int
}