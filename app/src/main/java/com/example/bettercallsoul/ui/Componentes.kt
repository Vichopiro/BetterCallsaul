package com.example.bettercallsoul.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.bettercallsoul.ui.theme.Amarillo
import com.example.bettercallsoul.ui.theme.Gris

@Composable
fun camposColores() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    errorTextColor = Color.White,
    focusedBorderColor = Amarillo,
    unfocusedBorderColor = Gris,
    focusedLabelColor = Amarillo,
    unfocusedLabelColor = Gris,
    cursorColor = Amarillo
)

@Composable
fun coloresBoton() = ButtonDefaults.buttonColors(
    containerColor = Amarillo,
    contentColor = Color.Black
)

@Composable
fun CampoFormulario(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    esClave: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        visualTransformation = if (esClave) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (esClave) KeyboardType.Password else tipoTeclado),
        colors = camposColores(),
        modifier = Modifier.fillMaxWidth()
    )
}