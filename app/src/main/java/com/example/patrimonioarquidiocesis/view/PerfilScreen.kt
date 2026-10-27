package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.patrimonioarquidiocesis.ui.theme.Dorado
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.view.components.EstadoRutaChip
import com.example.patrimonioarquidiocesis.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    navController: NavController,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val usuario by perfilViewModel.usuario.collectAsState()
    val rutas by perfilViewModel.rutas.collectAsState()
    val temaOscuro by perfilViewModel.temaOscuro.collectAsState()

    val datos = usuario
    if (datos == null) {
        Cargando()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar con la inicial del nombre
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Dorado, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = datos.nombre.take(1), fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
        Text(text = datos.nombre, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = datos.email, color = TintaSuave)
        Text(text = nombreRol(datos.rol), color = Dorado, fontWeight = FontWeight.Bold)

        // Tema oscuro (se guarda en SQLite)
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tema oscuro", modifier = Modifier.weight(1f))
                Switch(
                    checked = temaOscuro,
                    onCheckedChange = { perfilViewModel.cambiarTemaOscuro(it) }
                )
            }
        }

        // Mis rutas: la de BORRADOR se sigue armando, las otras abren su detalle
        Text(text = "Mis rutas", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
        Card(modifier = Modifier.fillMaxWidth()) {
            if (rutas.isEmpty()) {
                Text(text = "Aún no tienes rutas", color = TintaSuave, modifier = Modifier.padding(16.dp))
            }
            for (ruta in rutas) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (ruta.estado == Constantes.RUTA_BORRADOR) {
                                navController.navigate(Rutas.ARMAR_RUTA)
                            } else {
                                navController.navigate(Rutas.DETALLE_RUTA.replace("{rutaId}", ruta.rutaId.toString()))
                            }
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = ruta.nombreRuta, modifier = Modifier.weight(1f))
                    EstadoRutaChip(estado = ruta.estado)
                }
            }
        }

        // Opciones según el rol (regla 5)
        Card(modifier = Modifier.fillMaxWidth()) {
            FilaPerfil(texto = "Notificaciones") { navController.navigate(Rutas.NOTIFICACIONES) }
            if (datos.rol == Constantes.ROL_GUIA) {
                HorizontalDivider()
                FilaPerfil(texto = "Mis asignaciones") { navController.navigate(Rutas.MIS_ASIGNACIONES) }
            }
            if (datos.rol == Constantes.ROL_COLEGIO) {
                HorizontalDivider()
                FilaPerfil(texto = "Planificar salida pedagógica") { navController.navigate(Rutas.SALIDA_PEDAGOGICA) }
            }
            HorizontalDivider()
            FilaPerfil(texto = "Cerrar sesión", color = Terracota) {
                perfilViewModel.cerrarSesion {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
        }
    }
}

// Una fila del menú: texto a la izquierda y flecha a la derecha
@Composable
private fun FilaPerfil(texto: String, color: Color = Color.Unspecified, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = texto, color = color, modifier = Modifier.weight(1f))
        Text(text = "›", color = TintaSuave)
    }
}

// En la BD se guarda la constante; aquí se muestra el texto bonito
private fun nombreRol(rol: String): String {
    return when (rol) {
        Constantes.ROL_COLEGIO -> "Colegio / Delegación"
        Constantes.ROL_GUIA -> "Guía voluntario"
        Constantes.ROL_ENCARGADO -> "Encargado de templo"
        else -> "Turista"
    }
}
