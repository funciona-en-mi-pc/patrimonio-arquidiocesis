package com.example.patrimonioarquidiocesis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.patrimonioarquidiocesis.model.EventoCultural
import com.example.patrimonioarquidiocesis.model.AvisoNotificacion
import com.example.patrimonioarquidiocesis.dao.AsignacionDao
import com.example.patrimonioarquidiocesis.dao.AvisoDao
import com.example.patrimonioarquidiocesis.dao.ConfiguracionDao
import com.example.patrimonioarquidiocesis.dao.EventoDao
import com.example.patrimonioarquidiocesis.dao.ParadaDao
import com.example.patrimonioarquidiocesis.dao.RutaDao
import com.example.patrimonioarquidiocesis.dao.SalidaDao
import com.example.patrimonioarquidiocesis.dao.UsuarioDao
import com.example.patrimonioarquidiocesis.model.AsignacionGuia
import com.example.patrimonioarquidiocesis.model.ConfiguracionApp
import com.example.patrimonioarquidiocesis.model.ParadaRuta
import com.example.patrimonioarquidiocesis.model.RutaPatrimonial
import com.example.patrimonioarquidiocesis.model.SalidaPedagogica
import com.example.patrimonioarquidiocesis.model.Usuario

@Database(
    entities = [
        ConfiguracionApp::class, Usuario::class, EventoCultural::class, AvisoNotificacion::class,
        RutaPatrimonial::class, ParadaRuta::class, SalidaPedagogica::class, AsignacionGuia::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configuracionDao(): ConfiguracionDao
    abstract fun usuarioDao(): UsuarioDao

    abstract fun eventoDao(): EventoDao

    abstract fun avisoDao(): AvisoDao

    abstract fun rutaDao(): RutaDao
    abstract fun paradaDao(): ParadaDao
    abstract fun salidaDao(): SalidaDao
    abstract fun asignacionDao(): AsignacionDao

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