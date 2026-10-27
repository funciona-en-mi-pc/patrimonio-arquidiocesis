package com.example.patrimonioarquidiocesis.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.ui.theme.DoradoClaro
import com.example.patrimonioarquidiocesis.ui.theme.DoradoProfundo
import com.example.patrimonioarquidiocesis.ui.theme.PiedraOscura
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.TerracotaClaro
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave

// Estado de una ruta o de una asignación: CONFIRMADA, PENDIENTE, RECHAZADA o BORRADOR
@Composable
fun EstadoRutaChip(estado: String) {
    val fondo = when (estado) {
        Constantes.ASIGNACION_CONFIRMADA -> DoradoClaro
        Constantes.ASIGNACION_PENDIENTE -> TerracotaClaro
        else -> PiedraOscura
    }
    val colorTexto = when (estado) {
        Constantes.ASIGNACION_CONFIRMADA -> DoradoProfundo
        Constantes.ASIGNACION_PENDIENTE -> Terracota
        else -> TintaSuave
    }
    val texto = when (estado) {
        Constantes.ASIGNACION_CONFIRMADA -> "Confirmada"
        Constantes.ASIGNACION_PENDIENTE -> "Pendiente"
        Constantes.ASIGNACION_RECHAZADA -> "Rechazada"
        else -> "Borrador"
    }

    Text(
        text = texto,
        color = colorTexto,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(fondo, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}
