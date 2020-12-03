package br.emerson.pi4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import br.emerson.pi4.Model.PedidoItens
import br.emerson.pi4.Model.PedidoTotal
import kotlinx.android.synthetic.main.activity_item_pedido.*
import kotlinx.android.synthetic.main.item_pedido.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemPedidoActivity : AppCompatActivity() {

    // Inicia retrofit com a URL base
    val retrofit = RetrofitBase().base
    val pedidoService = retrofit.create( br.emerson.pi4.Service.PedidoService::class.java )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_pedido)
    }

    override fun onResume() {
        if( controleSessao().validarSessao() )
        {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
        else
        {
            caregarTotal()
            carregarItens()
        }

        super.onResume()
    }

    fun caregarTotal()
    {
       // val pedidoID = intent.getStringExtra( "PedidoID" )?.toInt()
        val pedidoTotal = intent.getStringExtra( "PedidoTotal" )

        tvItensPedidoTotal.text = "Total: ${pedidoTotal}"

//        val pedidoServiceCall = pedidoService.pedidoTotal ( "Bearer " + Sessao.UserToken, pedidoID!! )
//
//        val pedidoCallBack = object : Callback<PedidoTotal>
//        {
//            override fun onResponse(call: Call<PedidoTotal>, response: Response<PedidoTotal>)
//            {
//                if ( response.isSuccessful )
//                {
//                    val pedidoTotalRetorno = response.body()
//
//                    pedidoTotalRetorno?.let {
//                        tvItensPedidoTotal.text = "Total: " + pedidoTotalRetorno.total
//                    }
//                }
//                else
//                {
//                    avisoPosAcao(this@ItemPedidoActivity, getString(R.string.apiResposta) )
//                    Log.e( response.code().toString(), response.errorBody().toString() )
//                }
//            }
//
//            override fun onFailure(call: Call<PedidoTotal>, t: Throwable)
//            {
//                avisoPosAcao(this@ItemPedidoActivity, getString(R.string.apiRespostaFalha) )
//                Log.e( "ItemPedidoActivity", "caregarTotal", t )
//            }
//
//        }
//        pedidoServiceCall.enqueue( pedidoCallBack )
    }

    fun carregarItens()
    {
        progressBarItensPedido.visibility = View.VISIBLE

        containerItensPedido.removeAllViews()

        val pedidoID = intent.getStringExtra( "PedidoID" )?.toInt()
        val pedidoServiceCall = pedidoService.listPedidoItens( "Bearer " + Sessao.UserToken, pedidoID!! )

        val pedidoCallBack = object : Callback<List<PedidoItens>>
        {
            override fun onResponse( call: Call<List<PedidoItens>>, response: Response<List<PedidoItens>> )
            {
                if ( response.isSuccessful )
                {
                    val pedidoItensRetorno = response.body()

                    pedidoItensRetorno?.let {

                        for (pedidoItensRetorno in it) {

                            val itemPedido = layoutInflater.inflate( R.layout.item_pedido, containerItensPedido, false )

                            itemPedido.tvItemPedidoProduto.text = pedidoItensRetorno.produto
                            itemPedido.tvItemPedidoProduto.setOnClickListener {
                                val i = Intent(this@ItemPedidoActivity, ProdutoActivity::class.java)
                                i.putExtra("produtoID", pedidoItensRetorno.product_id.toString() )
                                startActivity(i)
                            }

                            itemPedido.tvItemPedidoQuantidade.text = pedidoItensRetorno.quantidade.toString()
                            itemPedido.tvItemPedidoValor.text = pedidoItensRetorno.valor
                            itemPedido.tvItemPedidoSubTotal.text = pedidoItensRetorno.sutotal

                            containerItensPedido.addView( itemPedido )
                        }

                        progressBarItensPedido.visibility = View.GONE
                    }
                }
                else
                {
                    avisoPosAcao(this@ItemPedidoActivity, getString(R.string.apiResposta) )
                    Log.e( response.code().toString(), response.errorBody().toString() )
                }
            }

            override fun onFailure(call: Call<List<PedidoItens>>, t: Throwable)
            {
                avisoPosAcao(this@ItemPedidoActivity, getString(R.string.apiRespostaFalha) )
                Log.e( "ItemPedidoActivity", "carregarItens", t )
            }
        }

        pedidoServiceCall.enqueue( pedidoCallBack )
    }
}