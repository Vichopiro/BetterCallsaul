package com.example.bettercallsoul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.bettercallsoul.ui.LoginScreen
import com.example.bettercallsoul.ui.Navegacion
import com.example.bettercallsoul.ui.RegistroScreen
import com.example.bettercallsoul.ui.theme.BetterCallSoulTheme
import com.example.bettercallsoul.viewmodel.LoginViewModel
import com.example.bettercallsoul.viewmodel.NormaViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val normaViewModel: NormaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BetterCallSoulTheme {

                //solo para cachar si funciona
                val usuario = loginViewModel.usuario
                when {
                    usuario != null -> Navegacion(
                        viewModel = normaViewModel,
                        nombre = usuario.nombre,
                        onLogout = loginViewModel::cerrarSesion
                    )
                    loginViewModel.mostrandoRegistro -> RegistroScreen(loginViewModel)
                    else -> LoginScreen(loginViewModel)
                }
            }
        }
    }
}