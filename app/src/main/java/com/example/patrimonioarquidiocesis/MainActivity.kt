package com.example.patrimonioarquidiocesis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patrimonioarquidiocesis.navigation.AppNavHost
import com.example.patrimonioarquidiocesis.ui.theme.PatrimonioArquidiocesisTheme
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val rutaInicial by mainViewModel.rutaInicial.collectAsState()
            val temaOscuro by mainViewModel.temaOscuro.collectAsState()

            PatrimonioArquidiocesisTheme(darkTheme = temaOscuro) {

                if (rutaInicial == null) {
                    Cargando()
                } else {
                    AppNavHost(rutaInicial!!)
                }
            }
        }
    }
}