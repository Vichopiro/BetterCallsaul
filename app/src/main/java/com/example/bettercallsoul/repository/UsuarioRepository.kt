package com.example.bettercallsoul.repository

import com.example.bettercallsoul.model.Usuario
import com.example.bettercallsoul.remote.ApiService
import com.example.bettercallsoul.remote.RetrofitInstance
import java.security.MessageDigest

class UsuarioRepository(
    private val api: ApiService = RetrofitInstance.crearApi()
){
    suspend fun registrar(nombre: String, apellido: String, correo: String, clave: String): Usuario =
        api.registrarUsuario(
            mapOf(
                "usuario" to mapOf(
                    "nombre" to nombre,
                    "apellido" to apellido,
                    "correo" to correo,
                    "clave" to encriptar(clave)
                )
            )
        )

    suspend fun iniciarSesion(correo: String, clave: String): Usuario =
        api.iniciarSesion(mapOf("correo" to correo, "clave" to encriptar(clave)))

    suspend fun buscarPorId(id: Int): Usuario = api.obtenerUsuario(id)

    suspend fun actualizarFoto(idUsuario: Int, ruta: String) {
        api.actualizarFoto(idUsuario, mapOf("usuario" to mapOf("fotoPath" to ruta)))
    }

    private fun encriptar(clave: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(clave.toByteArray())
            .joinToString("") { "%02x".format(it) }
}