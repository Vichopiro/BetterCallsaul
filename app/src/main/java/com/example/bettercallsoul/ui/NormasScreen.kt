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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun NormasScreen(
    viewModel: NormaViewModel,
    nombre: String,
    onLogout: () -> Unit,
    onNormaClick: (Int) -> Unit
) {
    val normas by viewModel.normas.collectAsState()
    val tamano by viewModel.tamanoLetra.collectAsState()
    val colores = ButtonDefaults.buttonColors(
        containerColor = Amarillo,
        contentColor = Color.Black
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Text(
            text = "¡Hola, $nombre!",
            color = Amarillo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Legislación disponible",
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
            items(normas) { norma ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = norma.nombre,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${norma.materia} · Modificada en ${norma.ultimaModificacion}",
                        color = Gris,
                        fontSize = 14.sp
                    )
                }
                HorizontalDivider(color = Gris)
            }
        }

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = Amarillo,
                contentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión", fontWeight = FontWeight.Bold)
        }
    }
}