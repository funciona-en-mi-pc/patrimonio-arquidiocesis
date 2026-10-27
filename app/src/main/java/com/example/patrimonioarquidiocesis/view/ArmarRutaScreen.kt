package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.patrimonioarquidiocesis.view.components.ParadaFila
import com.example.patrimonioarquidiocesis.viewmodel.ArmarRutaViewModel

@Composable
fun ArmarRutaScreen(
    navController: NavController,
    armarRutaViewModel: ArmarRutaViewModel = viewModel()
) {
    val ruta by armarRutaViewModel.ruta.collectAsState()
    val paradas by armarRutaViewModel.paradas.collectAsState()
    val templos by armarRutaViewModel.templos.collectAsState()

    if (ruta == null) {
        Cargando()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Arma tu ruta", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        if (paradas.isEmpty()) {
            Text(text = "Tu ruta está vacía. Agrega templos para empezar.", color = TintaSuave)
        }

        // Paradas numeradas; cada una se puede quitar
        paradas.forEachIndexed { indice, parada ->
            val nombre = templos.find { it.temploId == parada.temploId }?.nombreOficial ?: "Templo"
            ParadaFila(
                numero = indice + 1,
                nombreTemplo = nombre,
                detalle = if (indice == 0) "Punto de partida" else "+ ${Constantes.MINUTOS_TRASLADO} min de traslado",
                onQuitar = { armarRutaViewModel.quitar(parada.paradaId) }
            )
        }

        OutlinedButton(
            onClick = { armarRutaViewModel.alternarTemplos() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "+ Agregar otro templo")
        }

        // Lista de templos que todavía no están en la ruta
        if (armarRutaViewModel.mostrarTemplos) {
            Card(modifier = Modifier.fillMaxWidth()) {
                for (templo in templos) {
                    if (paradas.none { it.temploId == templo.temploId }) {
                        TextButton(onClick = { armarRutaViewModel.agregar(templo.temploId) }) {
                            Text(text = templo.nombreOficial)
                        }
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Tiempo estimado total", color = TintaSuave)
                Text(
                    text = "${armarRutaViewModel.minutosTotales(paradas)} minutos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (armarRutaViewModel.mensaje.isNotEmpty()) {
            Text(text = armarRutaViewModel.mensaje, color = Terracota)
        }

        Button(
            onClick = {
                armarRutaViewModel.confirmar { rutaId ->
                    navController.navigate(Rutas.DETALLE_RUTA.replace("{rutaId}", rutaId.toString())) {
                        // Al volver atrás no se regresa a la ruta ya confirmada
                        popUpTo(Rutas.ARMAR_RUTA) { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Confirmar ruta")
        }
    }
}
