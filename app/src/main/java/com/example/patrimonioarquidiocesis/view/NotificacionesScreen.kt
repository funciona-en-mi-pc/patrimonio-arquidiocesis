package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.viewmodel.NotificacionesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(
    navController: NavController,
    notificacionesViewModel: NotificacionesViewModel = viewModel()
) {
    val avisos by notificacionesViewModel.avisos.collectAsState()
    val nombresTemplos by notificacionesViewModel.nombresTemplos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notificaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val lista = avisos

        if (lista == null) {
            Cargando()
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lista) { aviso ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = nombresTemplos[aviso.temploId] ?: "Templo",
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = aviso.mensaje)
                            Text(
                                text = formatoFecha(aviso.fechaHora),
                                fontSize = 12.sp,
                                color = TintaSuave
                            )
                        }
                    }
                }
            }
        }
    }
}

// Convierte "2026-10-25T09:00" en "25/10 · 09:00"
private fun formatoFecha(fechaHora: String): String {
    val dia = fechaHora.substring(8, 10)
    val mes = fechaHora.substring(5, 7)
    val hora = fechaHora.substring(11, 16)
    return "$dia/$mes · $hora"
}