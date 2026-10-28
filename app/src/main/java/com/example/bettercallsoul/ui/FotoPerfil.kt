package com.example.bettercallsoul.ui

import android.Manifest
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.bettercallsoul.ui.theme.Amarillo
import com.example.bettercallsoul.ui.theme.Gris
import com.example.bettercallsoul.viewmodel.LoginViewModel
import java.io.File

@Composable
fun FotoPerfil(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val usuario = viewModel.usuario
    var rutaTemporal by rememberSaveable { mutableStateOf<String?>(null) }

    val tomarFoto = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exito ->
        val ruta = rutaTemporal
        if (exito && ruta != null) {
            viewModel.actualizarFoto(ruta)
        } else if (ruta != null) {
            File(ruta).delete()
        }
    }

    fun abrirCamara() {
        val id = usuario?.id ?: return
        val carpeta = File(context.filesDir, "fotos").apply { mkdirs() }
        val archivo = File(carpeta, "perfil_${id}_${System.currentTimeMillis()}.jpg")
        rutaTemporal = archivo.absolutePath
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", archivo)
        tomarFoto.launch(uri)
    }

    val pedirPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) abrirCamara()
    }

    val fotoPath = usuario?.fotoPath
    val imagen = remember(fotoPath) {
        fotoPath?.let { ruta ->
            val opciones = BitmapFactory.Options().apply { inSampleSize = 4 }
            BitmapFactory.decodeFile(ruta, opciones)?.asImageBitmap()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Gris),
            contentAlignment = Alignment.Center
        ) {
            if (imagen != null) {
                Image(
                    bitmap = imagen,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = usuario?.nombre?.take(1)?.uppercase().orEmpty(),
                    color = Color.Black,
                    fontSize = 32.sp
                )
            }
        }
        TextButton(onClick = { pedirPermiso.launch(Manifest.permission.CAMERA) }) {
            Text("Tomar foto", color = Amarillo)
        }
    }
}