package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.ConfiguracionApp
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(configuracion: ConfiguracionApp)

    @Query("SELECT * FROM configuracion WHERE id = 1")
    suspend fun leer(): ConfiguracionApp?

    @Query("SELECT * FROM configuracion WHERE id = 1")
    fun observar(): Flow<ConfiguracionApp?>
}