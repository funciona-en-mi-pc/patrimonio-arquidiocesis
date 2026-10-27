package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.patrimonioarquidiocesis.navigation.Rutas
import com.example.patrimonioarquidiocesis.ui.theme.DoradoProfundo
import com.example.patrimonioarquidiocesis.ui.theme.Piedra
import com.example.patrimonioarquidiocesis.ui.theme.Tinta
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.view.components.EstadoChip
import com.example.patrimonioarquidiocesis.viewmodel.PanelEncargadoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanelEncargadoScreen(
    navController: NavController,
    panelViewModel: PanelEncargadoViewModel = viewModel()
) {
    val templo by panelViewModel.templo.collectAsState()
    val estado by panelViewModel.estado.collectAsState()
    val eventos by panelViewModel.eventos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = "Panel del encargado", fontSize = 12.sp)
                        Text(text = templo?.nombreOficial ?: "")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        panelViewModel.cerrarSesion {
                            navController.navigate(Rutas.LOGIN) {
                                popUpTo(navController.graph.id) { inclusive = true }
                            }
                        }
                    }) {
                        Text(text = "Salir", color = Piedra)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Tinta,
                    titleContentColor = Piedra
                )
            )
        }
    ) { innerPadding ->
        if (templo == null) {
            Cargando()
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Disponibilidad de hoy
                Text(text = "Disponibilidad horaria", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Hoy", modifier = Modifier.weight(1f))
                        EstadoChip(estado = estado)
                    }
                }

                // Publicar aviso
                Text(text = "Publicar aviso", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = panelViewModel.mensajeAviso,
                    onValueChange = { panelViewModel.cambiarMensaje(it) },
                    label = { Text("Ej: Misa solemne especial este sábado a las 18:00") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { panelViewModel.publicarAviso() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Publicar aviso")
                }
                if (panelViewModel.confirmacion.isNotEmpty()) {
                    Text(text = panelViewModel.confirmacion, color = DoradoProfundo)
                }

                // Eventos del templo
                Text(text = "Eventos programados", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                eventos.forEach { evento ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = evento.nombreEvento, fontWeight = FontWeight.Bold)
                            Text(
                                text = evento.fechaHora.replace("T", " · "),
                                fontSize = 12.sp,
                                color = TintaSuave
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate(Rutas.NUEVO_EVENTO) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "+ Nuevo evento")
                }
            }
        }
    }
}

