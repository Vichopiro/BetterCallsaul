package com.example.bettercallsoul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bettercallsoul.ui.LoginScreen
import com.example.bettercallsoul.ui.SaludoScreen
import com.example.bettercallsoul.ui.theme.BetterCallSoulTheme
import com.example.bettercallsoul.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BetterCallSoulTheme {
                val usuario = viewModel.usuario
                if (usuario == null) {
                    LoginScreen(
                        nombre = viewModel.nombre,
                        clave = viewModel.clave,
                        onNombreChange = viewModel::onNombreChange,
                        onClaveChange = viewModel::onClaveChange,
                        onLogin = viewModel::login
                    )
                } else {
                    SaludoScreen(
                        nombre = usuario.nombre,
                        onLogout = viewModel::logout
                    )
                }
            }
        }
    }
}