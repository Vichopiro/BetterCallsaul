package com.example.bettercallsoul.repository

import com.example.bettercallsoul.model.Norma

class NormaRepository {
    fun getAll(): List<Norma> {
        return listOf(
            Norma(id = 1, nombre = "Código Civil", materia = "Derecho Civil", ultimaModificacion = 2024),
            Norma(id = 2, nombre = "Código Penal", materia = "Derecho Penal", ultimaModificacion = 2024),
            Norma(id = 3, nombre = "Código del Trabajo", materia = "Derecho Laboral", ultimaModificacion = 2024),
            Norma(id = 4, nombre = "Constitución Política", materia = "Derecho Constitucional", ultimaModificacion = 2023)
        )
    }
}