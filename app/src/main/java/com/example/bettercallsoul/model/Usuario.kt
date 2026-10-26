package com.example.bettercallsoul.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val fotoPath: String? = null
)