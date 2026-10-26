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

@Composable
fun EstadoChip(estado: String) {
    val fondo = when (estado) {
        Constantes.ESTADO_ABIERTO -> DoradoClaro
        Constantes.ESTADO_EN_CELEBRACION -> TerracotaClaro
        else -> PiedraOscura
    }
    val colorTexto = when (estado) {
        Constantes.ESTADO_ABIERTO -> DoradoProfundo
        Constantes.ESTADO_EN_CELEBRACION -> Terracota
        else -> TintaSuave
    }
    val texto = when (estado) {
        Constantes.ESTADO_ABIERTO -> "Abierto"
        Constantes.ESTADO_EN_CELEBRACION -> "En celebración"
        else -> "Cerrado"
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

