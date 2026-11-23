package com.example.bettercallsoul.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bettercallsoul.notificacion.Notificador
import com.example.bettercallsoul.ui.theme.Amarillo
import com.example.bettercallsoul.ui.theme.Fondo
import com.example.bettercallsoul.ui.theme.Gris
import com.example.bettercallsoul.viewmodel.EstadoCarga
import com.example.bettercallsoul.viewmodel.NormaViewModel

@Composable
fun ArticulosScreen(viewModel: NormaViewModel, normaId: Int, usuarioId: Int, onVolver: () -> Unit) {
    val normas by viewModel.normas.collectAsState()
    val articulos by viewModel.articulos.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()
    val estadoArticulos by viewModel.estadoArticulos.collectAsState()
    val tamano by viewModel.tamanoLetra.collectAsState()
    val norma = normas.find { it.id == normaId }
    val colores = buttonColors(

        containerColor = Amarillo,
        contentColor = Color.Black
    )

    val context = LocalContext.current
    val mensajeRecordatorio = "Revisa ${norma?.nombre ?: "esta norma"} cuando tengas un momento."
    // pide el permiso de las notis, si lo hace muestra el recordatorio
    val pedirPermisoNotificaciones = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            Notificador.mostrarRecordatorio(context, "Recordatorio de lectura", mensajeRecordatorio)
        }
    }

    LaunchedEffect(normaId) {
        viewModel.cargarArticulos(normaId)
        viewModel.cargarFavoritos(usuarioId)
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

        Box(modifier = Modifier.weight(1f)) {
            when (estadoArticulos) {
                EstadoCarga.Cargando -> IndicadorCarga()
                EstadoCarga.Error -> MensajeError(
                    mensaje = "No se pudieron cargar los artículos. Revisa tu conexión.",
                    onReintentar = { viewModel.cargarArticulos(normaId) }
                )
                EstadoCarga.Exito -> LazyColumn {
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
                            val esFavorito = favoritos.any { it.articuloId == articulo.id }
                            TextButton(onClick = { viewModel.alternarFavorito(usuarioId, articulo.id) }) {
                                Text(
                                    text = if (esFavorito) "★ Quitar de favoritos" else "☆ Guardar en favoritos",
                                    color = Amarillo
                                )
                            }
                        }
                        HorizontalDivider(color = Gris)
                    }
                }
            }
        }

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pedirPermisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    Notificador.mostrarRecordatorio(context, "Recordatorio de lectura", mensajeRecordatorio)
                }
            },
            colors = colores,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recordarme esta norma", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))


        Button(
            onClick = onVolver,
            colors = colores,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver", fontWeight = FontWeight.Bold)
        }
    }
}