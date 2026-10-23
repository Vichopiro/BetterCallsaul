package com.example.bettercallsoul.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.repository.NormaRepository
import kotlinx.coroutines.flow.MutableStateFlow

class NormaViewModel : ViewModel() {
    private val repository = NormaRepository()

    val normas = MutableStateFlow<List<Norma>>(value = emptyList())
    val articulos = MutableStateFlow<List<Articulo>>(emptyList())
    val tamanoLetra = MutableStateFlow(16)
    init {
        cargarNormas()
    }
    private fun cargarNormas() {
        normas.value = repository.getAll()
    }
    fun cargarArticulos(normaId: Int){
        articulos.value = repository.getArticulos(normaId)
    }
    fun aumentarLetra(){
        if (tamanoLetra.value < 28) tamanoLetra.value += 2
    }
    fun disminuirLetra(){
        if (tamanoLetra.value > 12) tamanoLetra.value -= 2
    }
}