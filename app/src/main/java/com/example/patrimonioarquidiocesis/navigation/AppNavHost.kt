package com.example.patrimonioarquidiocesis.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.patrimonioarquidiocesis.view.InicioScreen
import com.example.patrimonioarquidiocesis.view.LoginScreen
import com.example.patrimonioarquidiocesis.view.components.BottomBar
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.view.RegistroScreen

@Composable
fun AppNavHost(rutaInicial: String){
    val navController = rememberNavController()
    val rutasConBarra = listOf(Rutas.INICIO, Rutas.AGENDA, Rutas.ARMAR_RUTA, Rutas.PERFIL)
    val baskStarckEntry by navController.currentBackStackEntryAsState()
    val rutaAltual = baskStarckEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (rutaAltual in rutasConBarra){
                BottomBar(navController, rutaAltual)
            }
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = rutaInicial,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Rutas.LOGIN){
                LoginScreen(
                    onLoginExitoso = { usuario ->
                        val destino = if (usuario.rol == Constantes.ROL_ENCARGADO) Rutas.PANEL_ENCARGADO else Rutas.INICIO
                        navController.navigate(destino) {
                            popUpTo(Rutas.LOGIN) { inclusive = true }
                        }
                    },
                    onIrRegistro = {navController.navigate(Rutas.REGISTRO)}
                ) }

            composable(Rutas.REGISTRO){
                RegistroScreen(
                    onRegistroExitoso = {
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.LOGIN) { inclusive = true }
                        }
                    },
                    onIrLogin = { navController.popBackStack() }
                ) }

            composable(Rutas.INICIO){ InicioScreen(navController) }
            composable(Rutas.TEMPLO){ PantallaVacia("Ficha de templo") }
            composable(Rutas.HORARIOS){ PantallaVacia("Horarios") }
            composable(Rutas.AGENDA){ PantallaVacia("Agenda cultural") }
            composable(Rutas.NOTIFICACIONES){ PantallaVacia("Notificaciones") }
            composable(Rutas.PERFIL){ PantallaVacia("Perfil") }
            composable(Rutas.ARMAR_RUTA){ PantallaVacia("Armar ruta") }
            composable(Rutas.DETALLE_RUTA){ PantallaVacia("Detalle de ruta") }
            composable(Rutas.SALIDA_PEDAGOGICA){ PantallaVacia("Planificar salida") }
            composable(Rutas.MIS_ASIGNACIONES){ PantallaVacia("Mis asignaciones") }
            composable(Rutas.PANEL_ENCARGADO){ PantallaVacia("Panel del encargado") }
            composable(Rutas.NUEVO_EVENTO){ PantallaVacia("Nuevo evento") }
        }
    }
}
@Composable
private fun PantallaVacia(nombre: String){
    Text(
        text = nombre,
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
    )
}
