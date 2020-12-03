package br.emerson.pi4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import br.emerson.pi4.Model.Categoria
import br.emerson.pi4.Model.Produto
import br.emerson.pi4.Service.CategoriaService
import kotlinx.android.synthetic.main.fragment_frag_main.*
import kotlinx.android.synthetic.main.fragment_frag_main.view.*
import kotlinx.android.synthetic.main.lancamentos_mais_vendidos_produto.view.*
import kotlinx.android.synthetic.main.lista_produto.view.*
import kotlinx.android.synthetic.main.maincategoria.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    // Inicia retrofit com a URL base
    val retrofit = RetrofitBase().base

    override fun onResume( ) {
        super.onResume()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View?
    {
        val fragmento = inflater.inflate(R.layout.fragment_frag_main, container, false)

        // Carregar lista de categorias
        fragmento.tvCategoriasVerTodos.setOnClickListener {
            val frag = CategoriaFragment.newInstance()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragContainer, frag)?.commit()
        }

        // Abrir Fragmento de lista de produtos, mas exibindo lançamentos
        fragmento.tvLancamentosVerTodos.setOnClickListener {
            this.listaProdutos("lancamentos", getString(R.string.lancamentos), 0)
        }

        // Abrir Fragmento de lista de produtos, mas exibindo mais vendidos
        fragmento.tvMaisVendidosVerTodos.setOnClickListener {
            this.listaProdutos("maisvendidos", getString(R.string.maisVendidos), 0)
        }

        // Categorias
        val CategoriaService = retrofit.create( CategoriaService::class.java )
        val CategoriaCall = CategoriaService.listMain()

        val CategoriaCallBack = object : Callback<List<Categoria>>
        {
            override fun onResponse( call: Call<List<Categoria>>, response: Response<List<Categoria>> )
            {
                if ( response.isSuccessful )
                {
                    val categoriasRetorno = response.body()

                    fragmento.containerCategorias.removeAllViews()

                    categoriasRetorno?.let {
                        for ( categoriasretorno in it )
                        {
                            val cat =  inflater.inflate(R.layout.maincategoria, containerCategorias, false)

                            cat.btnCategoriaMain.text = categoriasretorno.name

                            cat.btnCategoriaMain.setOnClickListener {
                                listaProdutos( "categoria", categoriasretorno.name, categoriasretorno.id )
                            }

                            fragmento.containerCategorias.addView( cat )
                        }
                        fragmento.progressBarFragMainCategorias.visibility = View.GONE
                    }
                }
                else
                {
                    activity?.let { avisoPosAcao(it, getString(R.string.apiResposta) ) }
                    Log.e( response.code().toString(), response.errorBody().toString() )
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable)
            {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "MainFragment", "CategoriaMain", t )
            }
        }
        CategoriaCall.enqueue( CategoriaCallBack )


        // Lançamentos
        val LancamentosService = retrofit.create( br.emerson.pi4.Service.ProdutoService::class.java )
        val LancamentosCall = LancamentosService.listLancamentosMain()

        val LancamentosCallBack = object : Callback<List<Produto>>
        {
            override fun onResponse( call: Call<List<Produto>>, response: Response<List<Produto>> )
            {
                if ( response.isSuccessful )
                {
                    val LancamentosRetorno = response.body()

                    fragmento.containerLancamentos.removeAllViews()

                    LancamentosRetorno?.let {
                        for ( LancamentosRetorno in it )
                        {
                            val lancamentos =  inflater.inflate(R.layout.lancamentos_mais_vendidos_produto, containerLancamentos, false)

                            try {
                                lancamentos.imgLancamentosMaisVendidosProduto.setImageBitmap( converterBase64( LancamentosRetorno.image ) )
                            }
                            catch (e: Exception) {
                                // handler
                                //listaProdutos.imgListaProduto.setImageResource( R.drawable.produto_sem_imagem )
                                //  activity?.let { avisoPosAcao(it, "Não foi possivel carregar imagem" ) }
                            }
                            finally {
                                // optional finally block
                            }
                            //lancamentos.imgLancamentosMaisVendidosProduto.setImageBitmap( converterBase64( LancamentosRetorno.image) )

                            lancamentos.imgLancamentosMaisVendidosProduto.setOnClickListener {
                                abrirProduto( LancamentosRetorno.id )
                            }

                            lancamentos.tvLancamentosMaisVendidosProdutoTitulo.text = LancamentosRetorno.name
                            lancamentos.tvLancamentosMaisVendidosProdutoTitulo.setOnClickListener {
                                abrirProduto( LancamentosRetorno.id )
                            }

                            lancamentos.tvLancamentosMaisVendidosProdutoPreco.text = mascaraValor(LancamentosRetorno.price)

                            fragmento.containerLancamentos.addView( lancamentos )
                        }
                        fragmento.progressBarFragMainLancamentos.visibility= View.GONE
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
                Log.e( "MainFragment", "LancamentosMain", t )
            }
        }
        LancamentosCall.enqueue( LancamentosCallBack )


        // Mais Vendidos
        val maisVendidosService = retrofit.create( br.emerson.pi4.Service.ProdutoService::class.java )
        val maisVendidosCall = maisVendidosService.listMaisVendidosMain()

        val maisVendidosCallBack = object : Callback<List<Produto>>
        {
            override fun onResponse( call: Call<List<Produto>>, response: Response<List<Produto>> )
            {
                if ( response.isSuccessful )
                {
                    val maisVendidosRetorno = response.body()

                    fragmento.containerMaisVendidos.removeAllViews()

                    maisVendidosRetorno?.let {
                        for ( maisVendidosRetorno in it )
                        {
                            val maisVendido =  inflater.inflate(R.layout.lancamentos_mais_vendidos_produto, containerMaisVendidos, false)

                            try {
                                maisVendido.imgLancamentosMaisVendidosProduto.setImageBitmap( converterBase64( maisVendidosRetorno.image ) )
                            }
                            catch (e: Exception) {
                                // handler
                                //listaProdutos.imgListaProduto.setImageResource( R.drawable.produto_sem_imagem )
                                //  activity?.let { avisoPosAcao(it, "Não foi possivel carregar imagem" ) }
                            }
                            finally {
                                // optional finally block
                            }
                           // maisVendido.imgLancamentosMaisVendidosProduto.setImageBitmap( converterBase64( maisVendidosRetorno.image) )

                            maisVendido.imgLancamentosMaisVendidosProduto.setOnClickListener {
                                abrirProduto( maisVendidosRetorno.id )
                            }

                            maisVendido.tvLancamentosMaisVendidosProdutoTitulo.text = maisVendidosRetorno.name
                            maisVendido.tvLancamentosMaisVendidosProdutoTitulo.setOnClickListener {
                                abrirProduto( maisVendidosRetorno.id )
                            }

                            maisVendido.tvLancamentosMaisVendidosProdutoPreco.text = mascaraValor(maisVendidosRetorno.price)

                            fragmento.containerMaisVendidos.addView( maisVendido )
                        }
                        fragmento.progressBarFragMainMaisVendidos.visibility = View.GONE
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
                Log.e( "MainFragment", "MaisVendidosMain", t )
            }
        }
        maisVendidosCall.enqueue( maisVendidosCallBack )


        // Inflate the layout for this fragment
        return fragmento
    }

    // Carregar listagem de produtos, adaptar para depois carregar dependendo de quem chamou (lista de produtos, lançamentos ou mais vendidos)
    fun listaProdutos(tipoOrigem: String, origemDescricao: String,  origem: Int)
    {
        val frag = ProdutoListaFragment.newInstance( tipoOrigem, origemDescricao, origem )
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragContainer, frag)?.commit()
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
        fun newInstance() = MainFragment()
    }
}