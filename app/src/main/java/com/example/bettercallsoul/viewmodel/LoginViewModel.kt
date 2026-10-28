package com.example.bettercallsoul.viewmodel

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettercallsoul.data.AppDatabase
import com.example.bettercallsoul.model.Usuario
import com.example.bettercallsoul.repository.UsuarioRepository
import java.io.File
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UsuarioRepository(AppDatabase.obtener(application).usuarioDao())
    private val prefs = application.getSharedPreferences(NOMBRE_PREFS, Context.MODE_PRIVATE)
    var nombre by mutableStateOf("")
        private set
    var apellido by mutableStateOf("")
        private set
    var correo by mutableStateOf("")
        private set
    var clave by mutableStateOf("")
        private set

    var errorNombre by mutableStateOf<String?>(null)
        private set
    var errorApellido by mutableStateOf<String?>(null)
        private set
    var errorCorreo by mutableStateOf<String?>(null)
        private set
    var errorClave by mutableStateOf<String?>(null)
        private set
    var errorCredenciales by mutableStateOf<String?>(null)
        private set

    var mostrandoRegistro by mutableStateOf(false)
        private set
    var usuario by mutableStateOf<Usuario?>(null)
        private set
    var cargandoSesion by mutableStateOf(false)
        private set
    init {
        val idGuardado = prefs.getInt(CLAVE_ID_USUARIO, -1)
        if (idGuardado != -1) {
            cargandoSesion = true
            viewModelScope.launch {
                usuario = repository.buscarPorId(idGuardado)
                cargandoSesion = false
            }
        }
    }
    fun onNombreChange(valor: String) {
        nombre = valor
        errorNombre = null
    }

    fun onApellidoChange(valor: String) {
        apellido = valor
        errorApellido = null
    }

    fun onCorreoChange(valor: String) {
        correo = valor
        errorCorreo = null
        errorCredenciales = null
    }

    fun onClaveChange(valor: String) {
        clave = valor
        errorClave = null
        errorCredenciales = null
    }

    fun mostrarRegistro() {
        limpiarFormulario()
        mostrandoRegistro = true
    }

    fun mostrarLogin() {
        limpiarFormulario()
        mostrandoRegistro = false
    }

    fun iniciarSesion() {
        if (!validarCorreoYClave()) return
        viewModelScope.launch {
            val encontrado = repository.iniciarSesion(correo.trim().lowercase(), clave)
            if (encontrado == null) {
                errorCredenciales = MENSAJE_CREDENCIALES_INVALIDAS
            } else {
                guardarSesion(encontrado.id)
                usuario = encontrado
            }
        }
    }

    fun registrar() {
        val datosPersonalesValidos = validarNombreYApellido()
        val credencialesValidas = validarCorreoYClave()
        if (!datosPersonalesValidos || !credencialesValidas) return
        viewModelScope.launch {
            val nuevo = repository.registrar(
                nombre = nombre.trim(),
                apellido = apellido.trim(),
                correo = correo.trim().lowercase(),
                clave = clave
            )
            if (nuevo == null) {
                errorCorreo = MENSAJE_CORREO_REPETIDO
            } else {
                guardarSesion(nuevo.id)
                usuario = nuevo
            }
        }
    }

    fun actualizarFoto(ruta: String) {
        val actual = usuario ?: return
        viewModelScope.launch {
            repository.actualizarFoto(actual.id, ruta)
            actual.fotoPath?.let { File(it).delete() }
            usuario = actual.copy(fotoPath = ruta)
        }
    }

    fun cerrarSesion() {
        prefs.edit().remove(CLAVE_ID_USUARIO).apply()
        usuario = null
        mostrandoRegistro = false
        limpiarFormulario()
    }
    private fun guardarSesion(id: Int) {
        prefs.edit().putInt(CLAVE_ID_USUARIO, id).apply()
    }
    private fun validarNombreYApellido(): Boolean {
        errorNombre = if (nombre.isBlank()) "Ingresa tu nombre" else null
        errorApellido = if (apellido.isBlank()) "Ingresa tu apellido" else null
        return errorNombre == null && errorApellido == null
    }

    private fun validarCorreoYClave(): Boolean {
        errorCorreo = when {
            correo.isBlank() -> "Ingresa tu correo"
            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> "El correo no es válido"
            else -> null
        }
        errorClave = if (clave.length < LARGO_MINIMO_CLAVE) {
            "La contraseña debe tener al menos $LARGO_MINIMO_CLAVE caracteres"
        } else {
            null
        }
        return errorCorreo == null && errorClave == null
    }

    private fun limpiarFormulario() {
        nombre = ""
        apellido = ""
        correo = ""
        clave = ""
        errorNombre = null
        errorApellido = null
        errorCorreo = null
        errorClave = null
        errorCredenciales = null
    }

    private companion object {
        const val NOMBRE_PREFS = "sesion"
        const val CLAVE_ID_USUARIO = "id_usuario"
        const val LARGO_MINIMO_CLAVE = 6
        const val MENSAJE_CREDENCIALES_INVALIDAS = "Correo o contraseña incorrectos"
        const val MENSAJE_CORREO_REPETIDO = "Este correo ya está registrado"
    }
}