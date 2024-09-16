package com.example.hall9000.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

data class LoginRequest(val Username: String, val PasswordHash: String)

data class RegistrarRequest(val Username: String, val PasswordHash: String, val Email: String)

data class PedidoCompleto(
    val ClienteID: Int,
    val DataPedido: String,
    val Nome: String,
    val PedidoID: Int,
    val Valor: String
)
data class LoginResponse(
    val CreatedAt: String,
    val Email: String,
    val LoginID: Int,
    val PasswordHash: String,
    val Username: String
)

interface ApiService {
    @POST("auth/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<Void>

    @POST("logins/")
    fun registrarUser(@Body loginWithEmailRequest: RegistrarRequest): Call<Void>

    @GET("pedidos_completos/")
    fun getPedidos(): Call<List<PedidoCompleto>>

    @GET("logins/")
    fun getLogins(): Call<List<LoginResponse>>
}

