package com.example.bettercallsoul.repository

import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.remote.RetrofitInstance

class NormaRepository {
    suspend fun obtenerNormas(): List<Norma> = RetrofitInstance.api.obtenerNormas()

    suspend fun obtenerArticulos(normaId: Int): List<Articulo> =
        RetrofitInstance.api.obtenerArticulos(normaId)
}