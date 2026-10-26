package com.example.patrimonioarquidiocesis.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patrimonioarquidiocesis.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario): Long

    @Query("SELECT * FROM usuario WHERE email = :email")
    suspend fun buscarPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuario WHERE usuarioId = :id")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT COUNT(*) FROM usuario")
    suspend fun contar(): Int
}