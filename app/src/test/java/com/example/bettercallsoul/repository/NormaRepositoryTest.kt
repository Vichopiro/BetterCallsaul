package com.example.bettercallsoul.repository

import com.example.bettercallsoul.remote.RetrofitInstance
import com.example.bettercallsoul.model.Norma
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NormaRepositoryTest {
    private lateinit var servidor: MockWebServer
    private lateinit var repository: NormaRepository

    @Before
    fun preparar() {
        servidor = MockWebServer()
        servidor.start()
        val api = RetrofitInstance.crearApi(servidor.url("/").toString())
        repository = NormaRepositoryImpl(api)
    }

    @After
    fun limpiar() {
        servidor.shutdown()
    }

    @Test
    fun `consulta GET norma y convierte la respuesta`() = runTest {
        servidor.enqueue(
            MockResponse().setBody(
                """[{"id":1,"nombre":"Código Civil","materia":"Derecho Civil","ultimaModificacion":2024}]"""
            )
        )

        val resultado = repository.obtenerNormas()

        assertEquals(listOf(Norma(1, "Código Civil", "Derecho Civil", 2024)), resultado)
        val solicitud = servidor.takeRequest()
        assertEquals("GET", solicitud.method)
        assertEquals("/norma", solicitud.path)
    }
}