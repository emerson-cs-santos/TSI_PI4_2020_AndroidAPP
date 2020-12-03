package br.emerson.pi4.Service

import br.emerson.pi4.Model.Produto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProdutoService
{
    @GET("/api/lancamentosmain")
    fun listLancamentosMain(): Call<List<Produto>>

    @GET("/api/mais_vendidosmain")
    fun listMaisVendidosMain(): Call<List<Produto>>

    @GET("/api/produtos")
    fun listProdutos(): Call<List<Produto>>

    @GET("/api/lancamentos")
    fun listLancamentos(): Call<List<Produto>>

    @GET("/api/mais_vendidos")
    fun listMaisVendios(): Call<List<Produto>>

    @GET("/api/categoria_produtos/{id}")
    fun listCategoriaProdutos( @Path(value = "id") categoriaId: Int ): Call<List<Produto>>

    @GET("/api/busca/{busca}")
    fun listBuscaProdutos( @Path(value = "busca") buscarValor: String ): Call<List<Produto>>

    @GET("/api/produto/{id}")
    fun getProduto( @Path(value = "id") produtoId: Int ): Call<List<Produto>>

}