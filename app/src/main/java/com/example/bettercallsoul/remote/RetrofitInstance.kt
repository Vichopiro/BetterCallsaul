package com.example.bettercallsoul.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val URL_BASE = "https://x8ki-letl-twmt.n7.xano.io/api:1dWuZne8/"

    fun crearApi(urlBase: String = URL_BASE): ApiService =
        Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}