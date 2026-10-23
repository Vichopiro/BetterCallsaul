package com.example.bettercallsoul.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bettercallsoul.viewmodel.NormaViewModel

@Composable
fun Navegacion(viewModel: NormaViewModel, nombre: String, onLogout: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "normas") {
        composable("normas") {
            NormasScreen(
                viewModel = viewModel,
                nombre = nombre,
                onLogout = onLogout,
                onNormaClick = { id -> navController.navigate("articulos/$id") }
            )
        }
        composable("articulos/{normaId}") { backStackEntry ->
            val normaId = backStackEntry.arguments?.getString("normaId")?.toInt() ?: 0
            ArticulosScreen(
                viewModel = viewModel,
                normaId = normaId,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}