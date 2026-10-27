package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.SalidaPedagogica

@Dao
interface SalidaDao {

    @Insert
    suspend fun insertar(salida: SalidaPedagogica)

    @Query("SELECT * FROM salidaPedagogica")
    suspend fun leerTodas(): List<SalidaPedagogica>
}
