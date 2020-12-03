package br.emerson.pi4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.emerson.pi4.Model.Carrinho
import br.emerson.pi4.Model.CarrinhoRemover
import br.emerson.pi4.Model.CarrinhoTotal
import br.emerson.pi4.Model.Resposta
import kotlinx.android.synthetic.main.carrinho_item.view.*
import kotlinx.android.synthetic.main.fragment_carrinho.*
import kotlinx.android.synthetic.main.fragment_carrinho.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarrinhoFragment : Fragment() {

    val retrofit = RetrofitBase().base
    val carrinhoService = retrofit.create( br.emerson.pi4.Service.CarrinhoService::class.java )

    override fun onResume() {
        if( controleSessao().validarSessao() )
        {
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }
        else
        {
            carregarTotal()
            carregarCarrinho()
        }
        super.onResume()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_carrinho, container, false)

        fragmento.btnCarrinhoFinalizar.setOnClickListener {
            finalizarCompra()
        }

        return fragmento
    }

    fun finalizarCompra()
    {
        var finalizarCompraServiceCall = carrinhoService.finalizarCompra( "Bearer " + Sessao.UserToken, Sessao.UserID )

        val finalizarCompraCallBack = object : Callback<Resposta>
        {
            override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {
                if ( response.isSuccessful )
                {
                    val finalizarCompraRetorno = response.body()

                    if ( finalizarCompraRetorno!!.success == "true" )
                    {
                        activity?.let { avisoPosAcao(it, getString(R.string.pedidoFinalizado) + finalizarCompraRetorno!!.message ) }
                        carregarTotal()
                        tvCarrinhototal.text = "Total: R$0,0"
                        carregarCarrinho()
                    }
                    else
                    {
                        this@CarrinhoFragment.activity?.let {
                            alert( getString(R.string.problemas), finalizarCompraRetorno!!.message,
                                it
                            )
                        }
                    }
                }
                else
                {
                    Log.e(response.code().toString(), response.errorBody().toString())
                    activity?.let { avisoPosAcao(it, getString(R.string.loginExpirou) ) }
                }
            }

            override fun onFailure(call: Call<Resposta>, t: Throwable) {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "CarrinhoFragment", "finalizarCompra", t )
            }
        }
        finalizarCompraServiceCall.enqueue( finalizarCompraCallBack )
    }

    fun carregarTotal()
    {
        var carrinhoServiceCall = carrinhoService.getCarrinhoTotal( "Bearer " + Sessao.UserToken, Sessao.UserID )

        val carrinhoTotalCallBack = object : Callback<CarrinhoTotal>
        {
            override fun onResponse(call: Call<CarrinhoTotal>, response: Response<CarrinhoTotal>) {
                if ( response.isSuccessful )
                {
                    val carrinhoTotalRetorno = response.body()

                    tvCarrinhototal.text = "Total: " + carrinhoTotalRetorno!!.total
                }
                else
                {
                    Log.e(response.code().toString(), response.errorBody().toString())
                    activity?.let { avisoPosAcao(it, getString(R.string.loginExpirou) ) }
                }
            }

            override fun onFailure(call: Call<CarrinhoTotal>, t: Throwable) {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "CarrinhoFragment", "carregarTotal", t )
            }
        }
        carrinhoServiceCall.enqueue( carrinhoTotalCallBack )
    }

    fun carregarCarrinho()
    {
        containerCarrinho.removeAllViews()

        progressBarCarrinho.visibility = View.VISIBLE

        var carrinhoServiceCall = carrinhoService.listCarrinho( "Bearer " + Sessao.UserToken, Sessao.UserID )

        val ProdutosCallBack = object : Callback<List<Carrinho>>
        {
            override fun onResponse( call: Call<List<Carrinho>>, response: Response<List<Carrinho>>) {

                if ( response.isSuccessful )
                {
                    val carrinhoRetorno = response.body()

                    carrinhoRetorno?.let {
                        for (carrinhoRetorno in it) {
                            val carrinhoItem =  this@CarrinhoFragment.layoutInflater.inflate(R.layout.carrinho_item, containerCarrinho, false)

                            try {
                                carrinhoItem.imgCarrinhoItem.setImageBitmap( converterBase64( carrinhoRetorno.imagem ) )
                            }
                            catch (e: Exception) {
                                // handler
                                //listaProdutos.imgListaProduto.setImageResource( R.drawable.produto_sem_imagem )
                                //  activity?.let { avisoPosAcao(it, "Não foi possivel carregar imagem" ) }
                            }
                            finally {
                                // optional finally block
                            }
                           // carrinhoItem.imgCarrinhoItem.setImageBitmap( converterBase64( carrinhoRetorno.imagem ) )

                            carrinhoItem.imgCarrinhoItem.setOnClickListener {
                                abrirProduto( carrinhoRetorno.produto_id  )
                            }

                            carrinhoItem.tvCarrinhoItemTitulo.text = carrinhoRetorno.titulo
                            carrinhoItem.tvCarrinhoItemTitulo.setOnClickListener {
                                abrirProduto( carrinhoRetorno.produto_id  )
                            }

                            carrinhoItem.tvCarrinhoItemPlataforma.text = carrinhoRetorno.plataforma
                            carrinhoItem.tvCarrinhoItemPreco.text = carrinhoRetorno.preco
                            carrinhoItem.tvCarrinhoItemSubTotal.text = carrinhoRetorno.subtotal
                            carrinhoItem.tvCarrinhoItemQuantidade.text = carrinhoRetorno.quantidade

                            carrinhoItem.tvCarrinhoItemRemover.setOnClickListener {
                                // Chamar API, se retornar true, então remover da view
                                removerItem( carrinhoRetorno.produto_id )

                                containerCarrinho.removeView(carrinhoItem)
                                activity?.let { it1 -> avisoPosAcao(it1, getString(R.string.carrinhoItemRemovido) ) }
                                carregarTotal()
                            }

                            carrinhoItem.btnCarrinhoItemAdd.setOnClickListener {
                                abrirProduto( carrinhoRetorno.produto_id  )
                            }

                            containerCarrinho.addView(carrinhoItem)
                        }
                        progressBarCarrinho.visibility = View.GONE
                    }
                }
                else
                {
                    Log.e(response.code().toString(), response.errorBody().toString())
                    activity?.let { avisoPosAcao(it, getString(R.string.loginExpirou) ) }
                }
            }

            override fun onFailure(call: Call<List<Carrinho>>, t: Throwable) {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "CarrinhoFragment", "carregarCarrinho", t )
            }
        }
        carrinhoServiceCall.enqueue( ProdutosCallBack )
    }

    fun removerItem( produto_id: Int )
    {
        progressBarCarrinho.visibility = View.VISIBLE

        val carrinhoItemRemover = CarrinhoRemover(
            produto_id
        )

        var carrinhoRemoverItemServiceCall = carrinhoService.delCarrinhoItem ( "Bearer " + Sessao.UserToken, Sessao.UserID , carrinhoItemRemover )

        val carrinhoRemoverCallBack = object : Callback<Resposta>
        {
            override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {
                if ( response.isSuccessful )
                {
                    val carrinhoRemoverRetorno = response.body()

                    if ( carrinhoRemoverRetorno!!.success == "true" )
                    {
                    }
                    else
                    {
                        this@CarrinhoFragment.activity?.let {
                            alert( getString(R.string.problemas), carrinhoRemoverRetorno!!.message,
                                it
                            )
                        }
                    }
                }
                else
                {
                    Log.e(response.code().toString(), response.errorBody().toString())
                    activity?.let { avisoPosAcao(it, getString(R.string.loginExpirou) ) }
                }
                progressBarCarrinho.visibility = View.GONE
            }

            override fun onFailure(call: Call<Resposta>, t: Throwable) {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "CarrinhoFragment", "removerItem", t )
            }

        }
        carrinhoRemoverItemServiceCall.enqueue( carrinhoRemoverCallBack )
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
        fun newInstance() = CarrinhoFragment()
    }
}