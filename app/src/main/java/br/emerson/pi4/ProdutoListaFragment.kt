package br.emerson.pi4

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import br.emerson.pi4.Model.Produto
import kotlinx.android.synthetic.main.fragment_list_prod.*
import kotlinx.android.synthetic.main.fragment_list_prod.view.*
import kotlinx.android.synthetic.main.lista_produto.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoListaFragment : Fragment() {

    // Inicia retrofit com a URL base
    val retrofit = RetrofitBase().base

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_list_prod, container, false)

        val ListaProdutosService = retrofit.create( br.emerson.pi4.Service.ProdutoService::class.java )
        var ProdutosServiceCall = ListaProdutosService.listProdutos()

        val origemChamada = arguments?.getString("origem_key")
        var descricaoChamada = arguments?.getString("origemDesc")
        val origemID = arguments?.getInt("origemID")

        // Tipos de origemChamada:
            // main
                //  Vem da opção de jogos da navigation view, carregar todos os jogos

            // lancamentos
                // Vem da opção ver todos da tela principal

            // maisvendidos
                // Vem da opção todos da tela principal

            // categoria
                // Produtos da categoria, usar a varivavel origemID, pois é o ID da categoria

            // busca
                // Vem da opção de busca por texto

            // De der Tempo: itenspedido
                // Produtos de um pedido, usar a varivavel origemID, pois é o ID do Pedido

        // Esse já é feito quando a variavel ProdutosServiceCall é criada acima, mas a chamada de metodo pode mudar, mas sim, fazer o if abaixo não muda, mas preferi deixar para ficar claro as opções de origem
        if ( origemChamada == "main" )
        {
            ProdutosServiceCall = ListaProdutosService.listProdutos()
        }

        if ( origemChamada == "lancamentos" )
        {
            ProdutosServiceCall = ListaProdutosService.listLancamentos()
        }

        if ( origemChamada == "maisvendidos" )
        {
            ProdutosServiceCall = ListaProdutosService.listMaisVendios()
        }

        if ( origemChamada == "categoria" )
        {
            ProdutosServiceCall = ListaProdutosService.listCategoriaProdutos( origemID!! )
        }

        if ( origemChamada == "busca" )
        {
            ProdutosServiceCall = ListaProdutosService.listBuscaProdutos( descricaoChamada!! )
            descricaoChamada = getString(R.string.procurar) + ": " + descricaoChamada
        }

        fragmento.tvListagemProdutos.text = descricaoChamada

        // Mais Vendidos
        val ProdutosCallBack = object : Callback<List<Produto>>
        {
            override fun onResponse( call: Call<List<Produto>>, response: Response<List<Produto>>)
            {
                if ( response.isSuccessful )
                {
                    val produtosRetorno = response.body()

                    fragmento.containerListaProdutos.removeAllViews()

                    produtosRetorno?.let {
                        for ( produtosRetorno in it )
                        {
                            val listaProdutos =  inflater.inflate(R.layout.lista_produto, containerListaProdutos, false)

                            try {
                                listaProdutos.imgListaProduto.setImageBitmap( converterBase64( produtosRetorno.image ) )
                            }
                            catch (e: Exception) {
                                // handler
                                Log.e( "imgBase64QueFalhou", produtosRetorno.image )
                                //listaProdutos.imgListaProduto.setImageResource( R.drawable.produto_sem_imagem )
                               //  activity?.let { avisoPosAcao(it, "Não foi possivel carregar imagem" ) }
                            }
                            finally {
                                // optional finally block
                            }

//                            try {
//
//                                var base64String = produtosRetorno.image
//
//                                base64String = base64String.replace("data:image/jpeg;base64,","")
//                                val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
//                                val decodedImageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//
//                                listaProdutos.imgListaProduto.setImageBitmap( decodedImageBitmap )
//
//                                // listaProdutos.imgListaProduto.setImageBitmap( converterBase64( produtosRetorno.image ) )
//                            }
//                            catch (e: Exception) {
//                                // handler
//                                //listaProdutos.imgListaProduto.setImageResource( R.drawable.produto_sem_imagem )
//                               // activity?.let { avisoPosAcao(it, "Erro imagem" ) }
//                            }
//                            finally {
//                                // optional finally block
//                            }

                            listaProdutos.imgListaProduto.setOnClickListener {
                                abrirProduto(produtosRetorno.id)
                            }

                            listaProdutos.tvListaProdutoTitulo.text = produtosRetorno.name
                            listaProdutos.tvListaProdutoTitulo.setOnClickListener {
                                abrirProduto(produtosRetorno.id)
                            }

                            listaProdutos.tvListaProdutoPlataforma.text = produtosRetorno.categoryName
                            listaProdutos.tvListaProdutoPreco.text = mascaraValor( produtosRetorno.price )

                            fragmento.containerListaProdutos.addView(listaProdutos)
                        }
                        fragmento.progressBarListaProdutos.visibility = View.GONE
                    }
                }
                else
                {
                    activity?.let { avisoPosAcao(it, getString(R.string.apiResposta) ) }
                    Log.e( response.code().toString(), response.errorBody().toString() )
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable)
            {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "ProdutoListaFragment", "onCreateView", t )
            }
        }
        ProdutosServiceCall.enqueue( ProdutosCallBack )


        return fragmento
    }

    // Abrir intent produto
    fun abrirProduto( produtoID: Int )
    {
        val i = Intent(activity, ProdutoActivity::class.java)
        i.putExtra("produtoID", produtoID.toString() )
        startActivity(i)
    }

    companion object {
        @JvmStatic
        fun newInstance(origem: String, origemDesc: String, origemID: Int): ProdutoListaFragment {
            val frag = ProdutoListaFragment()

            val bundle = Bundle().apply {
                putString("origem_key", origem)
                putString("origemDesc", origemDesc)
                putInt("origemID", origemID)
            }

            frag.arguments = bundle

            return frag
        }
    }
}