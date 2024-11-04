package com.example.hall9000.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

data class LoginRequest(val email: String, val senha: String)

data class RegistrarRequest(val nome: String, val senha: String, val email: String)

data class UpdateRequest(val email: String, val senha: String, val nome: String)
data class DeleteRequest(val email: String)

data class PedidoCompleto(
    val CLIENTEID: Int,
    val DATAPEDIDO: String,
    val NOME: String,
    val PEDIDOID: Int,
    val VALOR: String
)
data class LoginResponse(
    val email: String,
    val senha: String,
    val nome: String
)

interface ApiService {
    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<Void>

    @POST("create-login")
    fun registrarUser(@Body loginWithEmailRequest: RegistrarRequest): Call<Void>

    @GET("pedidos_completos")
    fun getPedidos(): Call<List<PedidoCompleto>>

    @GET("recuperar-senha")
    fun getLogins(): Call<List<LoginResponse>>

    @PUT("login-update")
    fun updateUser(@Body updateRequest: UpdateRequest): Call<Void>

    @DELETE("login-delete")
    fun deleteUser(@Query("email") email: String): Call<Void>
}

