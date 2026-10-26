package com.example.bettercallsoul.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): UsuarioEntity?

    @Query("UPDATE usuarios SET fotoPath = :ruta WHERE id = :id")
    suspend fun actualizarFoto(id: Int, ruta: String)
}