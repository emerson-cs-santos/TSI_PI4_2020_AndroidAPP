package br.emerson.pi4.Service

import br.emerson.pi4.Model.Categoria
import retrofit2.Call
import retrofit2.http.GET

interface CategoriaService {

    @GET("/api/categoriasmain")
    fun listMain(): Call<List<Categoria>>

    @GET("/api/categorias")
    fun list(): Call<List<Categoria>>
}