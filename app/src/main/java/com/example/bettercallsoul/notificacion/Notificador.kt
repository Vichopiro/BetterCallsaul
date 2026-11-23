package com.example.bettercallsoul.notificacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.bettercallsoul.R


/** muestra notis locales para recordar la lec de una norma */
object Notificador {
    private const val ID_CANAL = "recordatorios_normas"
    private const val NOMBRE_CANAL = "Recordatorios de normas"

    // est epermiso se verifica en tienePermiso() antes de notificar
    @SuppressLint("MissingPermission")
    fun mostrarRecordatorio(context: Context, titulo: String, mensaje: String) {
        if (!tienePermiso(context)) return
        crearCanal(context)
        val notificacion = NotificationCompat.Builder(context, ID_CANAL)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        // El id cambia en cada llamada para que no se reemplace la notificación anterior
        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), notificacion)
    }

    // Desde Android 13 hay que pedir el permiso en tiempo de ejecucion
    private fun tienePermiso(context: Context): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

    // Desde Android 8 toda notificación necesita un canal
    private fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val canal = NotificationChannel(
            ID_CANAL,
            NOMBRE_CANAL,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(canal)
    }
}