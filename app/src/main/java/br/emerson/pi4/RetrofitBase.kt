package br.emerson.pi4

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBase {

    // Configurar TimeOut
    // connectTimeout = Tempo para se conectar
    // readTimeout = Tempo para baixar ou fazer upload dos dados
    // retryOnConnectionFailure = Faz mais tentativas de conexão em caso de falha
    val client = OkHttpClient.Builder()
        .connectTimeout(800, TimeUnit.SECONDS)
        .readTimeout(800, TimeUnit.SECONDS)
        .writeTimeout(800, TimeUnit.SECONDS)
        .callTimeout(800, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    val base = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client( client )
        .build()
}