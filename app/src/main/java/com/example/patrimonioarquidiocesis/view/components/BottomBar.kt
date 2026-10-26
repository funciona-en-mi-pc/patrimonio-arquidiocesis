package com.example.patrimonioarquidiocesis.view.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.example.patrimonioarquidiocesis.navigation.Rutas

@Composable
fun BottomBar(navController: NavController, rutaActual: String?){
    NavigationBar {
        NavigationBarItem(
            selected = rutaActual == Rutas.INICIO,
            onClick = { navController.navigate(Rutas.INICIO)},
            icon = {Icon(Icons.Default.LocationOn, contentDescription = "Mapa")},
            label = { Text("Mapa") },
        )

        NavigationBarItem(
            selected = rutaActual == Rutas.AGENDA,
            onClick = { navController.navigate(Rutas.AGENDA) },
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Eventos") },
            label = { Text("Eventos") }
        )

        NavigationBarItem(
            selected = rutaActual == Rutas.ARMAR_RUTA,
            onClick = { navController.navigate(Rutas.ARMAR_RUTA)},
            icon = {Icon(Icons.Default.List, contentDescription = "Rutas") },
            label = { Text("Rutas") }
        )
        NavigationBarItem(
            selected = rutaActual == Rutas.INICIO,
            onClick = { navController.navigate(Rutas.INICIO)},
            icon = {Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}