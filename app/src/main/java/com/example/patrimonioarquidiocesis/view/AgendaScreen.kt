package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.EventoCultural
import com.example.patrimonioarquidiocesis.ui.theme.DoradoClaro
import com.example.patrimonioarquidiocesis.ui.theme.DoradoProfundo
import com.example.patrimonioarquidiocesis.ui.theme.Terracota
import com.example.patrimonioarquidiocesis.ui.theme.TintaSuave
import com.example.patrimonioarquidiocesis.view.components.Cargando
import com.example.patrimonioarquidiocesis.viewmodel.AgendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(agendaViewModel: AgendaViewModel = viewModel()) {
    val eventos by agendaViewModel.eventos.collectAsState()
    val nombresTemplos by agendaViewModel.nombresTemplos.collectAsState()
    val filtro by agendaViewModel.filtro.collectAsState()

    val lista = eventos

    if (lista == null) {
        Cargando()
    } else {
        // Aplica el filtro elegido
        val filtrados = if (filtro == "TODOS") lista else lista.filter { it.tipoEvento == filtro }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Agenda cultural", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            // Chips de filtro (se deslizan si no caben)
            val opciones = listOf("TODOS", Constantes.EVENTO_CONCIERTO, Constantes.EVENTO_EXPOSICION, Constantes.EVENTO_LITURGICO)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                opciones.forEach { opcion ->
                    FilterChip(
                        selected = filtro == opcion,
                        onClick = { agendaViewModel.cambiarFiltro(opcion) },
                        label = { Text(text = nombreTipo(opcion)) }
                    )
                }
            }

            if (filtrados.isEmpty()) {
                Text(text = "No hay eventos de este tipo")
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filtrados) { evento ->
                    EventoItem(evento = evento, nombreTemplo = nombresTemplos[evento.temploId] ?: "")
                }
            }
        }
    }
}

// Tarjeta de un evento: fecha a la izquierda, datos a la derecha
@Composable
private fun EventoItem(evento: EventoCultural, nombreTemplo: String) {
    val meses = listOf("ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC")
    // fechaHora viene como "2026-10-25T18:00"
    val dia = evento.fechaHora.substring(8, 10)
    val mes = meses[evento.fechaHora.substring(5, 7).toInt() - 1]
    val hora = evento.fechaHora.substring(11, 16)

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(DoradoClaro, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = dia, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DoradoProfundo)
                Text(text = mes, fontSize = 12.sp, color = DoradoProfundo)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = evento.nombreEvento, fontWeight = FontWeight.Bold)
                Text(text = "$nombreTemplo · $hora", fontSize = 12.sp, color = TintaSuave)
                Text(text = nombreTipo(evento.tipoEvento), fontSize = 12.sp, color = Terracota, fontWeight = FontWeight.Bold)
                if (evento.descripcion.isNotBlank()) {
                    Text(text = evento.descripcion, fontSize = 13.sp)
                }
            }
        }
    }
}

// Convierte el valor guardado en un texto bonito
private fun nombreTipo(tipo: String): String {
    return when (tipo) {
        Constantes.EVENTO_CONCIERTO -> "Concierto"
        Constantes.EVENTO_EXPOSICION -> "Exposición"
        Constantes.EVENTO_LITURGICO -> "Litúrgico"
        else -> "Todos"
    }
}