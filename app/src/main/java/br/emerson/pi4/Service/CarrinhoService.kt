package br.emerson.pi4.Service

import br.emerson.pi4.Model.*
import retrofit2.Call
import retrofit2.http.*

interface CarrinhoService {
    @Headers("Content-Type: application/json")
    @POST("/api/carrinho_incluir")
    fun carrinhoAddItem(@Header("Authorization") auth: String, @Body CarrinhoData: CarrinhoIncluir): Call<Resposta>

    @GET("/api/carrinho/{id}")
    fun listCarrinho( @Header("Authorization") auth: String, @Path(value = "id") userId: Int ): Call<List<Carrinho>>

    @GET("/api/carrinho_valor_total/{id}")
    fun getCarrinhoTotal( @Header("Authorization") auth: String, @Path(value = "id") userId: Int ): Call<CarrinhoTotal>

    @Headers("Content-Type: application/json")
    @PUT("/api/carrinho_remover/{id}")
    fun delCarrinhoItem( @Header("Authorization") auth: String, @Path(value = "id") userId: Int, @Body carrinhoRemoverItem: CarrinhoRemover ): Call<Resposta>

    @GET("/api/carrinho_finalizar/{id}")
    fun finalizarCompra( @Header("Authorization") auth: String, @Path(value = "id") userId: Int ): Call<Resposta>
}