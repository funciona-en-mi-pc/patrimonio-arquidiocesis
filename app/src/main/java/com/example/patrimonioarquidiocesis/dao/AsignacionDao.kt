package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.AsignacionGuia
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignacionDao {

    @Insert
    suspend fun insertar(asignacion: AsignacionGuia)

    @Query("SELECT * FROM asignacionGuia WHERE guiaId = :guiaId ORDER BY fechaAsignacion")
    fun listarDeGuia(guiaId: Int): Flow<List<AsignacionGuia>>

    @Query("SELECT * FROM asignacionGuia WHERE rutaId = :rutaId LIMIT 1")
    suspend fun buscarDeRuta(rutaId: Int): AsignacionGuia?

    @Query("UPDATE asignacionGuia SET estadoAsignacion = :estado WHERE asignacionId = :asignacionId")
    suspend fun cambiarEstado(asignacionId: Int, estado: String)
}
