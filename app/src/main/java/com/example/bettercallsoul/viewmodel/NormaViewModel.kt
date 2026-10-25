package com.example.bettercallsoul.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.repository.NormaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NormaViewModel : ViewModel() {
    private val repository = NormaRepository()

    private val _normas = MutableStateFlow(repository.getAll())
    val normas: StateFlow<List<Norma>> = _normas.asStateFlow()

    private val _articulos = MutableStateFlow<List<Articulo>>(emptyList())
    val articulos: StateFlow<List<Articulo>> = _articulos.asStateFlow()

    private val _tamanoLetra = MutableStateFlow(TAMANO_INICIAL)
    val tamanoLetra: StateFlow<Int> = _tamanoLetra.asStateFlow()

    fun cargarArticulos(normaId: Int) {
        _articulos.value = repository.getArticulos(normaId)
    }

    fun aumentarLetra() {
        if (_tamanoLetra.value < TAMANO_MAXIMO) _tamanoLetra.value += PASO_LETRA
    }

    fun disminuirLetra() {
        if (_tamanoLetra.value > TAMANO_MINIMO) _tamanoLetra.value -= PASO_LETRA
    }

    private companion object {
        const val TAMANO_INICIAL = 16
        const val TAMANO_MINIMO = 12
        const val TAMANO_MAXIMO = 28
        const val PASO_LETRA = 2
    }
}