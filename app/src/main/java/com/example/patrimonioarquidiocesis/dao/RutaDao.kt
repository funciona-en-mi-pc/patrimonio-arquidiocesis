package com.example.patrimonioarquidiocesis.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaDao {

    @Insert
    suspend fun insertar(ruta: RutaPatrimonial): Long

    @Update
    suspend fun actualizar(ruta: RutaPatrimonial)

    @Query("SELECT * FROM rutaPatrimonial WHERE rutaId = :rutaId")
    suspend fun buscarPorId(rutaId: Int): RutaPatrimonial?

    @Query("SELECT * FROM rutaPatrimonial WHERE usuarioCreadorId = :usuarioId ORDER BY rutaId DESC")
    fun listarDeUsuario(usuarioId: Int): Flow<List<RutaPatrimonial>>

    @Query("SELECT * FROM rutaPatrimonial WHERE usuarioCreadorId = :usuarioId AND estado = :estado LIMIT 1")
    suspend fun buscarPorEstado(usuarioId: Int, estado: String): RutaPatrimonial?

    @Query("SELECT * FROM rutaPatrimonial")
    suspend fun leerTodas(): List<RutaPatrimonial>

    @Query("SELECT COUNT(*) FROM rutaPatrimonial")
    suspend fun contar(): Int
}
