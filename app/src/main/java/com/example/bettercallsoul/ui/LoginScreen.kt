package com.example.bettercallsoul.ui

import androidx.compose.animation.AnimatedVisibility
import com.example.bettercallsoul.R
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bettercallsoul.ui.theme.Amarillo
import com.example.bettercallsoul.ui.theme.Fondo
import com.example.bettercallsoul.ui.theme.Gris
import com.example.bettercallsoul.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var tituloVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { tituloVisible = true }

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
        AnimatedVisibility(visible = tituloVisible, enter = fadeIn() + slideInVertically { -it }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.logosaulo),
                    contentDescription = "Logo de Código CH",
                    modifier = Modifier.size(350.dp)
                )
                Text(
                    text = "CÓDIGO CH",
                    color = Amarillo,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "¿Tienes problemas legales?",
                    color = Gris,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

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

        AnimatedVisibility(visible = viewModel.errorCredenciales != null) {
            Text(
                text = viewModel.errorCredenciales.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = viewModel::iniciarSesion,
            colors = coloresBoton(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión", fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = viewModel::mostrarRegistro) {
            Text("¿No tienes cuenta? Regístrate", color = Amarillo)
        }
    }
}