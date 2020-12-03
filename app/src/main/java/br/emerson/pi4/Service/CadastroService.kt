package br.emerson.pi4.Service

import br.emerson.pi4.Model.*
import retrofit2.Call
import retrofit2.http.*

interface CadastroService {
    @Headers("Content-Type: application/json")
    @POST("/api/registrar_usuario")
    fun addUsuario( @Body userData: CriarCadastro ): Call<Resposta>

    @Headers("Content-Type: application/json")
    @POST("/api/loginAPI")
    fun login( @Body loginData: Login ): Call<Resposta>

    @Headers("Content-Type: application/json")
    @PUT("/api/atualizar_usuario/{id}")
    fun alterarUsuario( @Header("Authorization") auth: String,  @Path(value = "id") userId: Int, @Body atualizarUser: AlterarCadastro ): Call<Resposta>

    @Headers("Content-Type: application/json")
    @PUT("/api/atualizar_senha/{id}")
    fun atualizarSenha( @Header("Authorization") auth: String,  @Path(value = "id") userId: Int, @Body atualizarUserSenha: AtualizarSenha): Call<Resposta>
}