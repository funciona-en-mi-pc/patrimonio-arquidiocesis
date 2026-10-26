package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.patrimonioarquidiocesis.navigation.Rutas
import com.example.patrimonioarquidiocesis.ui.theme.DoradoClaro
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.view.components.EstadoChip
import com.example.patrimonioarquidiocesis.viewmodel.FichaTemploViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaTemploScreen(
    navController: NavController,
    temploId: Int,
    fichaViewModel: FichaTemploViewModel = viewModel()
) {
    val templo by fichaViewModel.templo.collectAsState()
    val estado by fichaViewModel.estado.collectAsState()

    // Se ejecuta una vez al abrir la pantalla (o si cambia el temploId)
    LaunchedEffect(temploId) {
        fichaViewModel.cargar(temploId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = templo?.nombreOficial ?: "Templo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val datos = templo

        if (datos == null) {
            Cargando()
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Portada (en el mockup va una foto del templo)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(DoradoClaro, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text(text = datos.nombreOficial, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(text = datos.direccion, fontSize = 13.sp, color = TintaSuave)
                    }
                }

                EstadoChip(estado = estado)

                Text(text = "Reseña histórica", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = datos.descripcionHistorica)

                OutlinedButton(
                    onClick = {
                        navController.navigate(
                            Rutas.HORARIOS.replace("{temploId}", datos.temploId.toString())
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Ver horarios completos")
                }

                // Empuja el botón de abajo hasta el final de la pantalla
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { /* Pendiente: se conecta con RepositorioRutas de Javier */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Agregar a mi ruta")
                }
            }
        }
    }
}