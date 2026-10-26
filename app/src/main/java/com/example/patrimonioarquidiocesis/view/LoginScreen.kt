package com.example.patrimonioarquidiocesis.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patrimonioarquidiocesis.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    onIrRegistro: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ){
        Text(text = "Red de Patrimonio")
        Text(text = "Templos, rutas y memoria viva de la Arquidiocesis de Santiago")

        OutlinedTextField(
            value = loginViewModel.email,
            onValueChange = { loginViewModel.cambiarEmail(it) },
            label = { Text("Correo electronico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = loginViewModel.password,
            onValueChange = { loginViewModel.cambiarPassword(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (loginViewModel.iniciarSesion()){
                    onLoginExitoso()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sesion")
        }

        Text(text = loginViewModel.mensaje)

        TextButton(onClick = onIrRegistro) {
            Text(text ="¿No tiene cuenta? Registrate")
        }
    }
}