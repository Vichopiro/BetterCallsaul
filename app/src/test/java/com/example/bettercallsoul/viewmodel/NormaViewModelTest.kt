package com.example.bettercallsoul.viewmodel

import com.example.bettercallsoul.model.Articulo
import com.example.bettercallsoul.model.Favorito
import com.example.bettercallsoul.model.Norma
import com.example.bettercallsoul.repository.NormaRepository
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NormaViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun preparar() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `carga las normas y publica el resultado`() = runTest {
        val normas = listOf(Norma(1, "Código Civil", "Derecho Civil", 2024))
        val viewModel = NormaViewModel(RepositorioFalso { normas })

        advanceUntilIdle()

        assertEquals(EstadoCarga.Exito, viewModel.estadoNormas.value)
        assertEquals(normas, viewModel.normas.value)
    }

    @Test
    fun `un error de red publica el estado de error`() = runTest {
        val viewModel = NormaViewModel(RepositorioFalso { throw IOException("Sin conexión") })

        advanceUntilIdle()

        assertEquals(EstadoCarga.Error, viewModel.estadoNormas.value)
    }

    private class RepositorioFalso(
        private val respuestaNormas: suspend () -> List<Norma>
    ) : NormaRepository {
        override suspend fun obtenerNormas(): List<Norma> = respuestaNormas()
        override suspend fun obtenerArticulos(normaId: Int): List<Articulo> = emptyList()
        override suspend fun obtenerFavoritos(usuarioId: Int): List<Favorito> = emptyList()
        override suspend fun agregarFavorito(usuarioId: Int, articuloId: Int) {}
        override suspend fun eliminarFavorito(favoritoId: Int) {}
    }
}