package com.example.bettercallsoul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bettercallsoul.model.Usuario
import com.example.bettercallsoul.repository.UsuarioRepository

class LoginViewModel : ViewModel() {
    private val repository = UsuarioRepository()
    var nombre by mutableStateOf("")
        private set
    var clave by mutableStateOf("")
        private set
    var usuario by mutableStateOf<Usuario?>(null)
        private set

    fun onNombreChange(valor: String) {
        nombre = valor
    }

    fun onClaveChange(valor: String) {
        clave = valor
    }

    fun login() {
        usuario = repository.login(nombre)
    }
    fun logout() {
        usuario = null
        clave = ""
    }
}
