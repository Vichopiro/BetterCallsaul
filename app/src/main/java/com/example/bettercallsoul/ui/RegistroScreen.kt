package com.example.bettercallsoul.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bettercallsoul.ui.theme.Amarillo
import com.example.bettercallsoul.ui.theme.Fondo
import com.example.bettercallsoul.viewmodel.LoginViewModel

@Composable
fun RegistroScreen(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            color = Amarillo,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoFormulario(
            valor = viewModel.nombre,
            onCambio = viewModel::onNombreChange,
            etiqueta = "Nombre",
            error = viewModel.errorNombre
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoFormulario(
            valor = viewModel.apellido,
            onCambio = viewModel::onApellidoChange,
            etiqueta = "Apellido",
            error = viewModel.errorApellido
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoFormulario(
            valor = viewModel.correo,
            onCambio = viewModel::onCorreoChange,
            etiqueta = "Correo",
            error = viewModel.errorCorreo,
            tipoTeclado = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoFormulario(
            valor = viewModel.clave,
            onCambio = viewModel::onClaveChange,
            etiqueta = "Contraseña",
            error = viewModel.errorClave,
            esClave = true
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = viewModel::registrar,
            colors = coloresBoton(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta", fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = viewModel::mostrarLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = Amarillo)
        }
    }
}