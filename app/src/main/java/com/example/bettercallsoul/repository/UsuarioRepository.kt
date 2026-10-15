package com.example.bettercallsoul.repository

import com.example.bettercallsoul.model.Usuario

class UsuarioRepository {
    fun login(nombre: String): Usuario {
        return Usuario(id = 1, nombre = nombre, apellido = "", correo = "")
    }
}