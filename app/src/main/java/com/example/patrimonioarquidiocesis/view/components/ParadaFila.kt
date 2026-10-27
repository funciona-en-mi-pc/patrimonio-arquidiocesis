package com.example.patrimonioarquidiocesis.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patrimonioarquidiocesis.ui.theme.Dorado
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave

// Una parada de la ruta: número en un círculo, nombre del templo y detalle.
// Si onQuitar es null (por ejemplo en el detalle) no se muestra el basurero.
@Composable
fun ParadaFila(
    numero: Int,
    nombreTemplo: String,
    detalle: String,
    onQuitar: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Dorado, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = numero.toString(), color = Color.White, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = nombreTemplo, fontWeight = FontWeight.Bold)
            Text(text = detalle, fontSize = 13.sp, color = TintaSuave)
        }
        if (onQuitar != null) {
            IconButton(onClick = onQuitar) {
                Icon(Icons.Default.Delete, contentDescription = "Quitar parada")
            }
        }
    }
}
