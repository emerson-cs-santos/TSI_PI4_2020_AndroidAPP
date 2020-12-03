package br.emerson.pi4.Service

import br.emerson.pi4.Model.Pedido
import br.emerson.pi4.Model.PedidoItens
import br.emerson.pi4.Model.PedidoTotal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PedidoService {
    @GET("/api/pedidos/{id}")
    fun listPedidos( @Header("Authorization") auth: String, @Path(value = "id") userId: Int ): Call<List<Pedido>>

    @GET("/api/pedidos_itens/{id}")
    fun listPedidoItens( @Header("Authorization") auth: String, @Path(value = "id") pedidoId: Int ): Call<List<PedidoItens>>

    @GET("/api/pedido_valor_total/{id}")
    fun pedidoTotal( @Header("Authorization") auth: String, @Path(value = "id") pedidoId: Int ): Call<PedidoTotal>
}