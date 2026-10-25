package com.example.bettercallsoul.repository

import com.example.bettercallsoul.model.Articulo
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

    fun getArticulos(normaId: Int): List<Articulo> {
        return (1..3).map { n ->
            Articulo(
                id = n,
                normaId = normaId,
                numero = "Artículo $n",
                texto = "Texto de ejemplo del artículo $n. Este contenido es ficticio y sirve para probar la lectura y el tamaño de letra."
            )
        }
    }
}