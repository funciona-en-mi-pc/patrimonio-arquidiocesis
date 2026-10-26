package com.example.patrimonioarquidiocesis.data

import com.example.patrimonioarquidiocesis.model.Constantes
import com.example.patrimonioarquidiocesis.model.DisponibilidadHoraria
import com.example.patrimonioarquidiocesis.model.Templo
import kotlinx.coroutines.delay
import java.time.LocalDate

object RepositorioTemplos {

    suspend fun  listrTemplos(): List<Templo>{
        delay(1000)
        return DatosEjemplo.templos
    }

    suspend fun obtenerTemplo(temploId: Int): Templo? {
        delay(500)
        return DatosEjemplo.templos.find {it.temploId == temploId}
    }

    suspend fun horariosDe(temploId: Int): List<DisponibilidadHoraria> {
        delay(500)
        return DatosEjemplo.horarios.filter { it.temploId == temploId }
    }

    suspend fun estadoActualDe(temploId: Int): String {
        val dias = listOf("LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO")
        val hoy = dias[LocalDate.now().dayOfWeek.value - 1]

        val horarioDeHoy = horariosDe(temploId).find { it.diaSemana == hoy && !it.esExcepcional }

        return horarioDeHoy?.estado ?: Constantes.ESTADO_CERRADO
    }



    //FIN//
}