package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.TerracotaClaro
import com.example.patrimonioarquidiocesis.ui.theme.Tinta
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.view.components.EstadoChip
import com.example.patrimonioarquidiocesis.viewmodel.HorariosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorariosScreen(
    navController: NavController,
    temploId: Int,
    horariosViewModel: HorariosViewModel = viewModel()
) {
    val horarios by horariosViewModel.horarios.collectAsState()
    val nombreTemplo by horariosViewModel.nombreTemplo.collectAsState()
    val estado by horariosViewModel.estado.collectAsState()

    LaunchedEffect(temploId) {
        horariosViewModel.cargar(temploId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = "Horarios")
                        Text(text = nombreTemplo, fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val lista = horarios

        if (lista == null) {
            Cargando()
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Recuadro "Estado actual"
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Estado actual",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        EstadoChip(estado = estado)
                    }
                }

                Text(text = "Semana", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                // Separamos los horarios normales de las aperturas especiales
                val normales = lista.filter { !it.esExcepcional }
                val especiales = lista.filter { it.esExcepcional }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        normales.forEach { horario ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = nombreDia(horario.diaSemana),
                                    modifier = Modifier.weight(1f)
                                )
                                if (horario.estado == Constantes.ESTADO_CERRADO) {
                                    EstadoChip(estado = horario.estado)
                                } else {
                                    Text(
                                        text = "${horario.horaApertura} – ${horario.horaCierre}",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // Recuadro rosado si hay aperturas especiales
                especiales.forEach { especial ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = TerracotaClaro)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Apertura especial",
                                color = Terracota,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${nombreDia(especial.diaSemana)} · ${especial.horaApertura} – ${especial.horaCierre}",
                                color = Tinta
                            )
                        }
                    }
                }
            }
        }
    }
}

// Convierte "MIERCOLES" en "Miércoles" para mostrarlo en pantalla
private fun nombreDia(dia: String): String {
    return when (dia) {
        "LUNES" -> "Lunes"
        "MARTES" -> "Martes"
        "MIERCOLES" -> "Miércoles"
        "JUEVES" -> "Jueves"
        "VIERNES" -> "Viernes"
        "SABADO" -> "Sábado"
        "DOMINGO" -> "Domingo"
        else -> dia
    }
}