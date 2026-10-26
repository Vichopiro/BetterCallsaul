package com.example.bettercallsoul.repository

import com.example.bettercallsoul.data.UsuarioDao
import com.example.bettercallsoul.data.UsuarioEntity
import com.example.bettercallsoul.model.Usuario
import java.security.MessageDigest

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun registrar(nombre: String, apellido: String, correo: String, clave: String): Usuario? {
        if (usuarioDao.buscarPorCorreo(correo) != null) return null
        val nuevo = UsuarioEntity(
            nombre = nombre,
            apellido = apellido,
            correo = correo,
            claveHash = encriptar(clave)
        )
        val idGenerado = usuarioDao.insertar(nuevo).toInt()
        return nuevo.copy(id = idGenerado).aUsuario()
    }

    suspend fun iniciarSesion(correo: String, clave: String): Usuario? {
        val guardado = usuarioDao.buscarPorCorreo(correo) ?: return null
        return if (guardado.claveHash == encriptar(clave)) guardado.aUsuario() else null
    }

    suspend fun actualizarFoto(idUsuario: Int, ruta: String) {
        usuarioDao.actualizarFoto(idUsuario, ruta)
    }

    private fun encriptar(clave: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(clave.toByteArray())
            .joinToString("") { "%02x".format(it) }

    private fun UsuarioEntity.aUsuario() = Usuario(id, nombre, apellido, correo, fotoPath)
}