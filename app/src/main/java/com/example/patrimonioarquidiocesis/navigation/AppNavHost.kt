package com.example.patrimonioarquidiocesis.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.patrimonioarquidiocesis.view.InicioScreen
import com.example.patrimonioarquidiocesis.view.LoginScreen
import com.example.patrimonioarquidiocesis.view.components.BottomBar
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.view.AgendaScreen
import com.example.patrimonioarquidiocesis.view.ArmarRutaScreen
import com.example.patrimonioarquidiocesis.view.DetalleRutaScreen
import com.example.patrimonioarquidiocesis.view.MisAsignacionesScreen
import com.example.patrimonioarquidiocesis.view.PerfilScreen
import com.example.patrimonioarquidiocesis.view.SalidaPedagogicaScreen
import com.example.patrimonioarquidiocesis.view.FichaTemploScreen
import com.example.patrimonioarquidiocesis.view.HorariosScreen
import com.example.patrimonioarquidiocesis.view.NotificacionesScreen
import com.example.patrimonioarquidiocesis.view.NuevoEventoScreen
import com.example.patrimonioarquidiocesis.view.PanelEncargadoScreen
import com.example.patrimonioarquidiocesis.view.RegistroScreen

@Composable
fun AppNavHost(rutaInicial: String){
    val navController = rememberNavController()
    val rutasConBarra = listOf(Rutas.INICIO, Rutas.AGENDA, Rutas.ARMAR_RUTA, Rutas.PERFIL)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (rutaActual in rutasConBarra){
                BottomBar(navController, rutaActual)
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
            composable(
                route = Rutas.TEMPLO,
                arguments = listOf(navArgument("temploId") { type = NavType.IntType })
            ) { backStackEntry ->
                val temploId = backStackEntry.arguments?.getInt("temploId") ?: 0
                FichaTemploScreen(navController, temploId)
            }
            composable(
                route = Rutas.HORARIOS,
                arguments = listOf(navArgument("temploId") { type = NavType.IntType })
            ) { backStackEntry ->
                val temploId = backStackEntry.arguments?.getInt("temploId") ?: 0
                HorariosScreen(navController, temploId)
            }
            composable(Rutas.AGENDA){ AgendaScreen() }
            composable(Rutas.NOTIFICACIONES){ NotificacionesScreen(navController) }
            composable(Rutas.PERFIL){ PerfilScreen(navController) }
            composable(Rutas.ARMAR_RUTA){ ArmarRutaScreen(navController) }
            composable(
                route = Rutas.DETALLE_RUTA,
                arguments = listOf(navArgument("rutaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val rutaId = backStackEntry.arguments?.getInt("rutaId") ?: 0
                DetalleRutaScreen(navController, rutaId)
            }
            composable(Rutas.SALIDA_PEDAGOGICA){ SalidaPedagogicaScreen(navController) }
            composable(Rutas.MIS_ASIGNACIONES){ MisAsignacionesScreen(navController) }
            composable(Rutas.PANEL_ENCARGADO){ PanelEncargadoScreen(navController) }
            composable(Rutas.NUEVO_EVENTO){ NuevoEventoScreen(navController) }
        }
    }
}
