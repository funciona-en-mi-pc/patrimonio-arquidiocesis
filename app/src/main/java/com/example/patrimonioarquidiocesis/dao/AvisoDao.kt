package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.AvisoNotificacion
import kotlinx.coroutines.flow.Flow

@Dao
interface AvisoDao {

    @Insert
    suspend fun insertar(aviso: AvisoNotificacion)

    @Query("SELECT * FROM avisoNotificacion ORDER BY fechaHora DESC")
    fun listar(): Flow<List<AvisoNotificacion>>

    @Query("SELECT * FROM avisoNotificacion WHERE temploId = :temploId ORDER BY fechaHora DESC")
    fun listarDeTemplo(temploId: Int): Flow<List<AvisoNotificacion>>

    @Query("SELECT COUNT(*) FROM avisoNotificacion")
    suspend fun contar(): Int
}