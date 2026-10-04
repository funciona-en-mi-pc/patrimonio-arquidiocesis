package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.patrimonioarquidiocesis.viewmodel.RegistroViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CrearCuentaScreen(
    registroViewModel: RegistroViewModel = viewModel()
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Text(text = "Crea Tu cuenta")
        Text(text = "Unete a la red para explorar templos, planificar visitas y coordinar rutas patrimoniales.")

        OutlinedTextField(
            value = registroViewModel.nombre,
            onValueChange = { registroViewModel.cambiarNombre(it) },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = registroViewModel.mail,
            onValueChange = { registroViewModel.cambiarMail(it) },
            label = {Text("Correo Eletronico")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value =  registroViewModel.pass,
            onValueChange = { registroViewModel.cambiarPass(it)},
            label = {Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Text(text = "¿Como participas en la red?")

        BotontipoParticipacion(
            texto = "Turista",
            seleccionado = registroViewModel.tipoParticipacion == "Turista",
            onClick = {registroViewModel.cambiarTipoParticipacion("Turista")}
        )

        BotontipoParticipacion(
            texto = "Colegio / Delegacion",
            seleccionado = registroViewModel.tipoParticipacion == "Colegio / Delegacion",
            onClick = {registroViewModel.cambiarTipoParticipacion("Colegio / Delegacion")}
        )

        BotontipoParticipacion(
            texto = "Gruia voluntario",
            seleccionado = registroViewModel.tipoParticipacion == "Gruia voluntario",
            onClick = {registroViewModel.cambiarTipoParticipacion("Gruia voluntario")}
        )

        Text(text = "Los perfiles de Encargado de templo se habilitan por la adminitraciopn de cada reciento. ")

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Crear cuenta")
        }

        Text(text = "¿Ya tienes cuenta? Inicia sesion")
    }
}


@Composable
fun BotontipoParticipacion( //Funcion para el boton de tipo, con 3 parametros
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit // una funcion si resive nada no envia nada
){
    if (seleccionado){
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = texto)
        }
    }else  {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = texto)
        }
    }
}
