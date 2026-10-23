package com.example.bettercallsoul.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bettercallsoul.ui.theme.Amarillo
import com.example.bettercallsoul.ui.theme.Fondo
import com.example.bettercallsoul.ui.theme.Gris
import com.example.bettercallsoul.viewmodel.NormaViewModel

@Composable
fun ArticulosScreen(viewModel: NormaViewModel, normaId: Int, onVolver: () -> Unit) {
    val normas by viewModel.normas.collectAsState()
    val articulos by viewModel.articulos.collectAsState()
    val tamano by viewModel.tamanoLetra.collectAsState()
    val norma = normas.find { it.id == normaId }
    val colores = buttonColors(
        containerColor = Amarillo,
        contentColor = Color.Black
    )

    LaunchedEffect(normaId) {
        viewModel.cargarArticulos(normaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Text(
            text = norma?.nombre ?: "",
            color = Amarillo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = norma?.materia ?: "",
            color = Gris,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Tamaño de letra",
                color = Gris,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { viewModel.disminuirLetra() }, colors = colores) {
                Text("A-", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.aumentarLetra() }, colors = colores) {
                Text("A+", fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(articulos) { articulo ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = articulo.numero,
                        color = Amarillo,
                        fontSize = tamano.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = articulo.texto,
                        color = Color.White,
                        fontSize = tamano.sp
                    )
                }
                HorizontalDivider(color = Gris)
            }
        }

        Button(
            onClick = onVolver,
            colors = colores,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver", fontWeight = FontWeight.Bold)
        }
    }
}