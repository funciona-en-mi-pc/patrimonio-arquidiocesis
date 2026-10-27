package com.example.patrimonioarquidiocesis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.patrimonioarquidiocesis.model.EventoCultural
import com.example.patrimonioarquidiocesis.model.AvisoNotificacion
import com.example.patrimonioarquidiocesis.dao.AvisoDao
import com.example.patrimonioarquidiocesis.dao.ConfiguracionDao
import com.example.patrimonioarquidiocesis.dao.EventoDao
import com.example.patrimonioarquidiocesis.dao.UsuarioDao
import com.example.patrimonioarquidiocesis.model.ConfiguracionApp
import com.example.patrimonioarquidiocesis.model.Usuario

@Database(
    entities = [ConfiguracionApp::class, Usuario::class, EventoCultural::class, AvisoNotificacion::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configuracionDao(): ConfiguracionDao
    abstract fun usuarioDao(): UsuarioDao

    abstract fun eventoDao(): EventoDao

    abstract fun avisoDao(): AvisoDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obtener(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "patrimonio.db"
                ).fallbackToDestructiveMigration(true)
                    .build().also { instancia = it }
            }
        }
    }
}