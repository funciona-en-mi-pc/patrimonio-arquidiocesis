package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.ui.theme.Piedra
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.Tinta
import com.example.patrimonioarquidiocesis.viewmodel.NuevoEventoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoEventoScreen(
    navController: NavController,
    nuevoEventoViewModel: NuevoEventoViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = "Panel del encargado", fontSize = 12.sp)
                        Text(text = "Nuevo evento")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Piedra)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Tinta,
                    titleContentColor = Piedra
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nuevoEventoViewModel.nombre,
                onValueChange = { nuevoEventoViewModel.cambiarNombre(it) },
                label = { Text("Nombre del evento") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Tipo de evento")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = nuevoEventoViewModel.tipo == Constantes.EVENTO_CONCIERTO,
                    onClick = { nuevoEventoViewModel.cambiarTipo(Constantes.EVENTO_CONCIERTO) },
                    label = { Text("Concierto") }
                )
                FilterChip(
                    selected = nuevoEventoViewModel.tipo == Constantes.EVENTO_EXPOSICION,
                    onClick = { nuevoEventoViewModel.cambiarTipo(Constantes.EVENTO_EXPOSICION) },
                    label = { Text("Exposición") }
                )
                FilterChip(
                    selected = nuevoEventoViewModel.tipo == Constantes.EVENTO_LITURGICO,
                    onClick = { nuevoEventoViewModel.cambiarTipo(Constantes.EVENTO_LITURGICO) },
                    label = { Text("Litúrgico") }
                )
            }

            OutlinedTextField(
                value = nuevoEventoViewModel.fecha,
                onValueChange = { nuevoEventoViewModel.cambiarFecha(it) },
                label = { Text("Fecha (AAAA-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nuevoEventoViewModel.hora,
                onValueChange = { nuevoEventoViewModel.cambiarHora(it) },
                label = { Text("Hora (HH:MM)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nuevoEventoViewModel.descripcion,
                onValueChange = { nuevoEventoViewModel.cambiarDescripcion(it) },
                label = { Text("Descripción") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            if (nuevoEventoViewModel.mensaje.isNotEmpty()) {
                Text(text = nuevoEventoViewModel.mensaje, color = Terracota)
            }

            Button(
                onClick = {
                    nuevoEventoViewModel.publicar {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Publicar evento")
            }
        }
    }
}
