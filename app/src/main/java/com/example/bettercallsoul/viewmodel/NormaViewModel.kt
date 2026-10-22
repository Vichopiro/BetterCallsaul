package com.example.bettercallsoul.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.repository.NormaRepository
import kotlinx.coroutines.flow.MutableStateFlow

class NormaViewModel : ViewModel() {
    private val repository = NormaRepository()

    val normas = MutableStateFlow<List<Norma>>(value = emptyList())

    init {
        cargarNormas()
    }

    private fun cargarNormas() {
        normas.value = repository.getAll()
    }
}