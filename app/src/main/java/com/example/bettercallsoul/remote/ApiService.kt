package com.example.bettercallsoul.remote

import com.example.bettercallsoul.model.Norma
import retrofit2.http.GET

interface ApiService {
    @GET("Norma")
    suspend fun obtenerNormas(): List<Norma>
}