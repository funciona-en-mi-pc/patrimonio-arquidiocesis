package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.navigation.Rutas
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.view.components.EstadoRutaChip
import com.example.patrimonioarquidiocesis.view.components.ParadaFila
import com.example.patrimonioarquidiocesis.viewmodel.DetalleRutaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRutaScreen(
    navController: NavController,
    rutaId: Int,
    detalleViewModel: DetalleRutaViewModel = viewModel()
) {
    val ruta by detalleViewModel.ruta.collectAsState()
    val paradas by detalleViewModel.paradas.collectAsState()
    val templos by detalleViewModel.templos.collectAsState()
    val asignacion by detalleViewModel.asignacion.collectAsState()
    val nombreGuia by detalleViewModel.nombreGuia.collectAsState()
    val esColegio by detalleViewModel.esColegio.collectAsState()

    // Se ejecuta una vez al abrir la pantalla (o si cambia el rutaId)
    LaunchedEffect(rutaId) {
        detalleViewModel.cargar(rutaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = ruta?.nombreRuta ?: "Mi ruta patrimonial") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val datos = ruta

        if (datos == null) {
            Cargando()
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Paradas", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(text = "${detalleViewModel.minutosTotales(paradas)} min", color = TintaSuave)
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        paradas.forEachIndexed { indice, parada ->
                            val nombre = templos.find { it.temploId == parada.temploId }?.nombreOficial ?: "Templo"
                            ParadaFila(
                                numero = indice + 1,
                                nombreTemplo = nombre,
                                detalle = if (indice == 0) "Salida" else "+ ${Constantes.MINUTOS_TRASLADO} min traslado"
                            )
                        }
                    }
                }

                Text(text = "Guía voluntario", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Card(modifier = Modifier.fillMaxWidth()) {
                    val guia = asignacion
                    if (guia == null) {
                        Text(text = "Aún no hay un guía asignado", color = TintaSuave, modifier = Modifier.padding(16.dp))
                    } else {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Terracota, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = nombreGuia.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = nombreGuia, fontWeight = FontWeight.Bold)
                                Text(text = "Guía asignado", fontSize = 13.sp, color = TintaSuave)
                            }
                            EstadoRutaChip(estado = guia.estadoAsignacion)
                        }
                    }
                }

                if (esColegio) {
                    Button(
                        onClick = { navController.navigate(Rutas.SALIDA_PEDAGOGICA) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Planificar salida pedagógica")
                    }
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Volver al inicio")
                }
            }
        }
    }
}
