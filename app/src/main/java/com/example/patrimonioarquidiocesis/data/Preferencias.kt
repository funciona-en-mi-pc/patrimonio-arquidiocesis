package com.example.patrimonioarquidiocesis.data

import android.content.Context

class Preferencias(context: Context) {

    private val prefs = context.getSharedPreferences("patrimonio", Context.MODE_PRIVATE)

    // --- Sesión ---
    fun guardarSesion(usuarioId: Int) {
        prefs.edit().putInt("sesionUsuarioId", usuarioId).apply()
    }

    fun leerSesion(): Int? {
        return if (prefs.contains("sesionUsuarioId")) prefs.getInt("sesionUsuarioId", 0) else null
    }

    fun borrarSesion() {
        prefs.edit().remove("sesionUsuarioId").apply()
    }

    // --- Tema oscuro ---
    fun guardarTemaOscuro(activado: Boolean) {
        prefs.edit().putBoolean("temaOscuro", activado).apply()
    }

    fun leerTemaOscuro(): Boolean {
        return prefs.getBoolean("temaOscuro", false)
    }
}