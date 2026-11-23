package com.example.bettercallsoul.remote

import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Favorito
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.model.Usuario
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("norma")
    suspend fun obtenerNormas(): List<Norma>

    @GET("articulo")
    suspend fun obtenerArticulos(@Query("normaId") normaId: Int): List<Articulo>

    @POST("usuario")
    suspend fun registrarUsuario(@Body datos: Map<String, Any>): Usuario

    @POST("login")
    suspend fun iniciarSesion(@Body datos: Map<String, Any>): Usuario

    @GET("usuario/{usuarioId}")
    suspend fun obtenerUsuario(@Path("usuarioId") usuarioId: Int): Usuario

    @PATCH("usuario/{usuarioId}")
    suspend fun actualizarFoto(@Path("usuarioId") usuarioId: Int, @Body datos: Map<String, Any>): Usuario

    @GET("favorito")
    suspend fun obtenerFavoritos(@Query("usuarioId") usuarioId: Int): List<Favorito>

    @POST("favorito")
    suspend fun agregarFavorito(@Body datos: Map<String, Any>): Favorito

    @DELETE("favorito/{favoritoId}")
    suspend fun eliminarFavorito(@Path("favoritoId") favoritoId: Int)
}