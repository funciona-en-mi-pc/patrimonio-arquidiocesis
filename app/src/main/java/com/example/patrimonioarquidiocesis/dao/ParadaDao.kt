package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.ParadaRuta
import kotlinx.coroutines.flow.Flow

@Dao
interface ParadaDao {

    @Insert
    suspend fun insertar(parada: ParadaRuta)

    @Query("DELETE FROM paradaRuta WHERE paradaId = :paradaId")
    suspend fun eliminar(paradaId: Int)

    @Query("SELECT * FROM paradaRuta WHERE rutaId = :rutaId ORDER BY orden")
    fun listarDeRuta(rutaId: Int): Flow<List<ParadaRuta>>

    @Query("SELECT * FROM paradaRuta WHERE rutaId = :rutaId ORDER BY orden")
    suspend fun leerDeRuta(rutaId: Int): List<ParadaRuta>
}
