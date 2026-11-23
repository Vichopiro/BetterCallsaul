package com.example.bettercallsoul.repository

import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Favorito
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.remote.ApiService
import com.example.bettercallsoul.remote.RetrofitInstance
interface NormaRepository {
    suspend fun obtenerNormas(): List<Norma>
    suspend fun obtenerArticulos(normaId: Int): List<Articulo>
    suspend fun obtenerFavoritos(usuarioId: Int): List<Favorito>
    suspend fun agregarFavorito(usuarioId: Int, articuloId: Int)
    suspend fun eliminarFavorito(favoritoId: Int)
}
class NormaRepositoryImpl(
    private val api: ApiService = RetrofitInstance.crearApi()
) : NormaRepository {
    override suspend fun obtenerNormas(): List<Norma> = api.obtenerNormas()

    override suspend fun obtenerArticulos(normaId: Int): List<Articulo> =
        api.obtenerArticulos(normaId)

    override suspend fun obtenerFavoritos(usuarioId: Int): List<Favorito> =
        api.obtenerFavoritos(usuarioId)

    override suspend fun agregarFavorito(usuarioId: Int, articuloId: Int) {
        api.agregarFavorito(
            mapOf("favorito" to mapOf("usuarioId" to usuarioId, "articuloId" to articuloId))
        )
    }

    override suspend fun eliminarFavorito(favoritoId: Int) {
        api.eliminarFavorito(favoritoId)
    }
}