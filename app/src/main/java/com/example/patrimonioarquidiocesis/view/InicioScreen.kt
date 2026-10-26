package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.example.patrimonioarquidiocesis.navigation.Rutas
import com.example.patrimonioarquidiocesis.ui.theme.PiedraOscura
import com.example.patrimonioarquidiocesis.view.components.TemploCard
import com.example.patrimonioarquidiocesis.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    navController: NavController,
    inicioViewModel: InicioViewModel = viewModel()
) {
    val templos by inicioViewModel.templos.collectAsState()
    val estados by inicioViewModel.estados.collectAsState()

    val lista = templos

    if (lista == null) {
        // Mientras carga: ruedita al centro
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Encabezado: saludo + campana de notificaciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Hola", fontSize = 14.sp)
                    Text(text = "Explora la red", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                IconButton(onClick = { navController.navigate(Rutas.NOTIFICACIONES) }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                }
            }

            // Mapa simulado en el mockup
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(PiedraOscura, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Mapa interactivo")
            }

            Text(text = "Cerca de ti", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            // Fila horizontal de tarjetas
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(lista) { templo ->
                    TemploCard(
                        templo = templo,
                        estado = estados[templo.temploId] ?: Constantes.ESTADO_CERRADO,
                        onClick = {
                            navController.navigate(
                                Rutas.TEMPLO.replace("{temploId}", templo.temploId.toString())
                            )
                        }
                    )
                }
            }
        }
    }
}