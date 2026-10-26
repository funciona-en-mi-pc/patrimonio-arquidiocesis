package com.example.patrimonioarquidiocesis.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patrimonioarquidiocesis.model.Templo
import com.example.patrimonioarquidiocesis.ui.theme.DoradoClaro
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave

@Composable
fun TemploCard(
    templo: Templo,
    estado: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(220.dp)
    ) {
        // Franja de color arriba (en el mockup va la foto del templo)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(DoradoClaro)
        )

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = templo.nombreOficial,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = templo.direccion,
                fontSize = 12.sp,
                color = TintaSuave
            )
            Spacer(modifier = Modifier.height(8.dp))
            EstadoChip(estado = estado)
        }
    }
}

