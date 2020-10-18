package br.emerson.pi4

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_frag_main.*
import kotlinx.android.synthetic.main.fragment_frag_main.view.*
import kotlinx.android.synthetic.main.lancamentos_mais_vendidos_produto.view.*
import kotlinx.android.synthetic.main.maincategoria.view.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
        for ( x in 0..5 )
        {
           // fragmento.containerCategorias.removeAllViews()
            var teste: String = ""

            var categoriaID: Int = 0

            val cat =  inflater.inflate(R.layout.maincategoria, containerCategorias, false)

            if ( x == 0 )
            {
                teste = "Playstation"
            }

            if ( x == 1 )
            {
                teste = "Nintendo Switch"
            }

            if ( x == 2 )
            {
                teste = "Xbox One"
            }

            if ( x == 3 )
            {
                teste = "Nintendo Wii U"
            }

            if ( x == 4 )
            {
                teste = "Playtation Vita"
            }

            if ( x == 5 )
            {
                teste = "Nintendo 3DS"
            }

            cat.btnCategoriaMain.text = teste

            cat.btnCategoriaMain.setOnClickListener {
                this.listaProdutos( "categoria", teste, categoriaID )
            }

            fragmento.containerCategorias.addView( cat )
        }

        // Lançamentos
        for ( x in 0..5 )
        {
            val lancamentos =  inflater.inflate(R.layout.lancamentos_mais_vendidos_produto, containerLancamentos, false)

            lancamentos.imgLancamentosMaisVendidosProduto.setImageResource(R.drawable.advancewars)
            lancamentos.imgLancamentosMaisVendidosProduto.setOnClickListener {
                this.abrirProduto(0)
            }

            lancamentos.tvLancamentosMaisVendidosProdutoTitulo.text = "Advance Wars 3"
            lancamentos.tvLancamentosMaisVendidosProdutoTitulo.setOnClickListener {
                this.abrirProduto(0)
            }

            lancamentos.tvLancamentosMaisVendidosProdutoPreco.text = "R$350,00"

            fragmento.containerLancamentos.addView(lancamentos)

            // Colocar click para abrir, ver se é possivel colocar na imagem, link ou se precisa ter uma botão no layout para isso
        }

        // Mais Vendidos
        for ( x in 0..5 )
        {
            val maisVendidos = inflater.inflate(R.layout.lancamentos_mais_vendidos_produto, containerMaisVendidos, false)

            maisVendidos.imgLancamentosMaisVendidosProduto.setImageResource(R.drawable.bleach_blade_battlers_2)
            maisVendidos.imgLancamentosMaisVendidosProduto.setOnClickListener {
                this.abrirProduto(0)
            }

            maisVendidos.tvLancamentosMaisVendidosProdutoTitulo.text = "Bleach Battlers 2"
            maisVendidos.tvLancamentosMaisVendidosProdutoTitulo.setOnClickListener {
                this.abrirProduto(0)
            }

            maisVendidos.tvLancamentosMaisVendidosProdutoPreco.text = "R$119,00"

            fragmento.containerMaisVendidos.addView(maisVendidos)

            // Colocar click para abrir, ver se é possivel colocar na imagem, link ou se precisa ter uma botão no layout para isso
        }

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
        startActivity(i)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}