package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.view.components.EstadoRutaChip
import com.example.patrimonioarquidiocesis.viewmodel.MisAsignacionesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisAsignacionesScreen(
    navController: NavController,
    asignacionesViewModel: MisAsignacionesViewModel = viewModel()
) {
    val asignaciones by asignacionesViewModel.asignaciones.collectAsState()
    val nombresRutas by asignacionesViewModel.nombresRutas.collectAsState()
    val detalles by asignacionesViewModel.detalles.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mis asignaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val lista = asignaciones

        if (lista == null) {
            Cargando()
        } else if (lista.isEmpty()) {
            Text(text = "No tienes asignaciones", color = TintaSuave, modifier = Modifier.padding(innerPadding).padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lista) { asignacion ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = nombresRutas[asignacion.rutaId] ?: "Ruta",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                EstadoRutaChip(estado = asignacion.estadoAsignacion)
                            }
                            // "2026-11-12T10:00" -> "2026-11-12 · 10:00"
                            Text(text = asignacion.fechaAsignacion.replace("T", " · "), color = TintaSuave)
                            Text(text = detalles[asignacion.rutaId] ?: "", color = TintaSuave)

                            // Solo las pendientes se pueden responder
                            if (asignacion.estadoAsignacion == Constantes.ASIGNACION_PENDIENTE) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { asignacionesViewModel.responder(asignacion.asignacionId, true) },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = "Aceptar")
                                    }
                                    OutlinedButton(
                                        onClick = { asignacionesViewModel.responder(asignacion.asignacionId, false) },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = "Rechazar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
