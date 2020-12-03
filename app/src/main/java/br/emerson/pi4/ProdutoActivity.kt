package br.emerson.pi4

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.emerson.pi4.Model.CarrinhoIncluir
import br.emerson.pi4.Model.Produto
import br.emerson.pi4.Model.Resposta
import kotlinx.android.synthetic.main.activity_produto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoActivity : AppCompatActivity() {

    // Inicia retrofit com a URL base
    val retrofit = RetrofitBase().base

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

        btnProdutoAddCarrinho.setOnClickListener {
            addCarrinho()
        }

        getProdutoDados()
    }

    override fun onResume() {
       // getProdutoDados()
        super.onResume()
    }

    fun addCarrinho()
    {
        val produtoID = intent.getStringExtra( "produtoID" )?.toInt()

        val produto = CarrinhoIncluir(
            Sessao.UserID
            ,produtoID!!
        )

        val carrinhoService = retrofit.create( br.emerson.pi4.Service.CarrinhoService::class.java )
        var carrinhoServiceCall = carrinhoService.carrinhoAddItem( "Bearer " + Sessao.UserToken, produto )

        val carrinhoAddCallBack = object : Callback<Resposta>
        {
            override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {

                if ( response.isSuccessful )
                {
                    val addCarrinhoRetorno = response.body()

                    if ( addCarrinhoRetorno!!.success == "true" )
                    {
                        avisoPosAcao(this@ProdutoActivity, getString(R.string.carrinhoAdd) )
                    }
                    else
                    {
                        alert( getString(R.string.problemas), addCarrinhoRetorno!!.message, this@ProdutoActivity )
                    }
                }
                else
                {
                    Log.e(response.code().toString(), response.errorBody().toString())
                    controleSessao().resetarSessao()
                    avisoPosAcao(this@ProdutoActivity, getString(R.string.loginExpirou) )

                    val i = Intent(this@ProdutoActivity, LoginActivity::class.java)
                    startActivity(i)
                }
            }

            override fun onFailure(call: Call<Resposta>, t: Throwable) {
                avisoPosAcao(this@ProdutoActivity, getString(R.string.apiRespostaFalha) )
                Log.e( "ProdutoActivity", "addCarrinho" )
            }
        }
        carrinhoServiceCall.enqueue( carrinhoAddCallBack )
    }


    fun getProdutoDados()
    {
        val produtoID = intent.getStringExtra( "produtoID" )?.toInt()
        val ProdutoService = retrofit.create( br.emerson.pi4.Service.ProdutoService::class.java )
        var ProdutoServiceCall = ProdutoService.getProduto( produtoID!! )

        val ProdutoCallBack = object : Callback<List<Produto>>
        {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>)
            {
                if ( response.isSuccessful )
                {
                    val produtoRetorno = response.body()

                    produtoRetorno?.let{
                        for (produtoRetorno in it) {

                            try {
                                imgProduto.setImageBitmap( converterBase64( produtoRetorno.image ) )
                            }
                            catch (e: Exception) {
                                // handler
                                //listaProdutos.imgListaProduto.setImageResource( R.drawable.produto_sem_imagem )
                                //  activity?.let { avisoPosAcao(it, "Não foi possivel carregar imagem" ) }
                            }
                            finally {
                                // optional finally block
                            }
                            //imgProduto.setImageBitmap( converterBase64( produtoRetorno.image ) )

                            tvProdutoNome.text = produtoRetorno.name
                            tvProdutoPlataforma.text = produtoRetorno.categoryName

                            tvProdutoPrecoAntigo.text = mascaraValor( produtoRetorno.price )
                            tvProdutoPreco.text = ""

                            val valorDescontado = produtoRetorno.discount
                            if ( valorDescontado.isNotEmpty() )
                            {
                                tvProdutoPrecoAntigo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                                tvProdutoPreco.text =  mascaraValor( valorDescontado )
                            }

                            tvProdutoDescricao.text = produtoRetorno.desc

                            val estoque = produtoRetorno.stock
                            var estoqueMsg = ""

                            if ( estoque >= 10 )
                            {
                                estoqueMsg = getString(R.string.emEstoque)
                            }

                            if ( estoque < 10 )
                            {
                                estoqueMsg = getString(R.string.ultimasUnidades)
                            }

                            if ( estoque == 0 )
                            {
                                estoqueMsg = getString(R.string.indisponível)
                                tvProdutoEstoqueStatus.setTextColor(getColor(R.color.estoqueIndisponivel))
                            }

                            tvProdutoEstoqueStatus.text = estoqueMsg
                        }
                        progressBarProduto.visibility = View.GONE
                    }
                }
                else
                {
                    avisoPosAcao(this@ProdutoActivity, getString(R.string.apiResposta) )
                    Log.e( response.code().toString(), response.errorBody().toString() )
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                avisoPosAcao(this@ProdutoActivity, getString(R.string.apiRespostaFalha))
                Log.e( "ProdutoActivity", "getProdutoDados", t )
            }
        }
            ProdutoServiceCall.enqueue( ProdutoCallBack )

    }
}