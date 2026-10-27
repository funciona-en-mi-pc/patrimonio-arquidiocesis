package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.patrimonioarquidiocesis.ui.theme.DoradoProfundo
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.viewmodel.SalidaPedagogicaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalidaPedagogicaScreen(
    navController: NavController,
    salidaViewModel: SalidaPedagogicaViewModel = viewModel()
) {
    val usuario by salidaViewModel.usuario.collectAsState()
    val rutas by salidaViewModel.rutas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Salida pedagógica") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val colegio = usuario

        if (colegio == null) {
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
                Text(text = "Coordina la visita de tu curso a una ruta patrimonial.", color = TintaSuave)

                // El colegio sale de la cuenta: no se escribe
                OutlinedTextField(
                    value = colegio.institucion.ifBlank { colegio.nombre },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Colegio / Delegación") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "Ruta seleccionada")
                if (rutas.isEmpty()) {
                    Text(text = "No tienes rutas confirmadas todavía", color = TintaSuave)
                }
                for (ruta in rutas) {
                    FilterChip(
                        selected = salidaViewModel.rutaId == ruta.rutaId,
                        onClick = { salidaViewModel.cambiarRuta(ruta.rutaId) },
                        label = { Text("${ruta.nombreRuta} · ${ruta.tiempoEstimadoTotal.toInt()} min") }
                    )
                }

                OutlinedTextField(
                    value = salidaViewModel.fecha,
                    onValueChange = { salidaViewModel.cambiarFecha(it) },
                    label = { Text("Fecha de la salida (AAAA-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = salidaViewModel.cantidad,
                    onValueChange = { salidaViewModel.cambiarCantidad(it) },
                    label = { Text("Cantidad de estudiantes") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = salidaViewModel.observaciones,
                    onValueChange = { salidaViewModel.cambiarObservaciones(it) },
                    label = { Text("Observaciones") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                if (salidaViewModel.mensaje.isNotEmpty()) {
                    Text(
                        text = salidaViewModel.mensaje,
                        color = if (salidaViewModel.enviada) DoradoProfundo else Terracota
                    )
                }

                Button(
                    onClick = { salidaViewModel.enviar() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Enviar solicitud")
                }
            }
        }
    }
}
