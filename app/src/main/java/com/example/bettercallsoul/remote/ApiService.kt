package com.example.bettercallsoul.remote

import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Norma
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("norma")
    suspend fun obtenerNormas(): List<Norma>

    @GET("articulo")
    suspend fun obtenerArticulos(@Query("normaId") normaId: Int): List<Articulo>
}