package com.example.bettercallsoul.viewmodel
import retrofit2.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Favorito
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.repository.NormaRepository
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException

sealed interface EstadoCarga {
    data object Cargando : EstadoCarga
    data object Exito : EstadoCarga
    data object Error : EstadoCarga
}

class NormaViewModel(private val repository: NormaRepository) : ViewModel() {

    private val _normas = MutableStateFlow<List<Norma>>(emptyList())
    val normas: StateFlow<List<Norma>> = _normas.asStateFlow()

    private val _estadoNormas = MutableStateFlow<EstadoCarga>(EstadoCarga.Cargando)
    val estadoNormas: StateFlow<EstadoCarga> = _estadoNormas.asStateFlow()

    private val _articulos = MutableStateFlow<List<Articulo>>(emptyList())
    val articulos: StateFlow<List<Articulo>> = _articulos.asStateFlow()

    private val _estadoArticulos = MutableStateFlow<EstadoCarga>(EstadoCarga.Cargando)
    val estadoArticulos: StateFlow<EstadoCarga> = _estadoArticulos.asStateFlow()
    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList())
    val favoritos: StateFlow<List<Favorito>> = _favoritos.asStateFlow()

    private val _tamanoLetra = MutableStateFlow(TAMANO_INICIAL)
    val tamanoLetra: StateFlow<Int> = _tamanoLetra.asStateFlow()

    init {
        cargarNormas()
    }

    fun cargarNormas() {
        _estadoNormas.value = EstadoCarga.Cargando
        viewModelScope.launch {
            try {
                _normas.value = repository.obtenerNormas()
                _estadoNormas.value = EstadoCarga.Exito
            } catch (e: IOException) {
                _estadoNormas.value = EstadoCarga.Error
            } catch (e: HttpException) {
                _estadoNormas.value = EstadoCarga.Error
            }
        }
    }

    fun cargarArticulos(normaId: Int) {
        _articulos.value = emptyList()
        _estadoArticulos.value = EstadoCarga.Cargando
        viewModelScope.launch {
            try {
                _articulos.value = repository.obtenerArticulos(normaId)
                _estadoArticulos.value = EstadoCarga.Exito
            } catch (e: IOException) {
                _estadoArticulos.value = EstadoCarga.Error
            } catch (e: HttpException) {
                _estadoArticulos.value = EstadoCarga.Error
            }
        }
    }
    fun cargarFavoritos(usuarioId: Int) {
        viewModelScope.launch {
            try {
                _favoritos.value = repository.obtenerFavoritos(usuarioId)
            } catch (e: IOException) {
                _favoritos.value = emptyList()
            } catch (e: HttpException) {
                _favoritos.value = emptyList()
            }
        }
    }

    fun alternarFavorito(usuarioId: Int, articuloId: Int) {
        viewModelScope.launch {
            try {
                val existente = _favoritos.value.find { it.articuloId == articuloId }
                if (existente == null) {
                    repository.agregarFavorito(usuarioId, articuloId)
                } else {
                    repository.eliminarFavorito(existente.id)
                }
                _favoritos.value = repository.obtenerFavoritos(usuarioId)
            } catch (e: IOException) {
                // sin conexión: la lista de favoritos queda igual
            } catch (e: HttpException) {
                // error del servidor: la lista de favoritos queda igual
            }
        }
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
    class Factory(private val repository: NormaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NormaViewModel(repository) as T
        }
    }
}