package br.emerson.pi4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_frag_main.*
import kotlinx.android.synthetic.main.fragment_frag_main.view.*
import kotlinx.android.synthetic.main.fragment_list_prod.*
import kotlinx.android.synthetic.main.fragment_list_prod.view.*
import kotlinx.android.synthetic.main.lancamentos_mais_vendidos_produto.view.*
import kotlinx.android.synthetic.main.lista_produto.view.*

class listProdFrag : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_list_prod, container, false)

        val origemChamada = arguments?.getString("origem_key")

        // Tipos de origemChamada:
            // main:
                //  vem do FragMain, ao escolher a opção de jogos, deve ser exibido todos os jogos

            // Lancamentos
                // Vem da opção ver todos da tela principal

            // Mais vendidos
                // Vem da opção todos da tela principal

            // Nome de uma categoria
                // Nesse caso a variavel origemChamada já foi preenchida acima e não precisa ser alterada com a string como as outras origens. A categoria é passado como parametro para esse fragmento.

        var tituloOrigem: String? = origemChamada

        if ( origemChamada == "main" )
        {
            tituloOrigem = getString(R.string.jogos)
        }

        if ( origemChamada == "lancamentos" )
        {
            tituloOrigem = getString(R.string.lancamentos)
        }

        if ( origemChamada == "maisvendidos" )
        {
            tituloOrigem = getString(R.string.maisVendidos)
        }

        fragmento.tvListagemProdutos.text = tituloOrigem

        for ( x in 0..5 )
        {
            val listaProdutos =  inflater.inflate(R.layout.lista_produto, containerListaProdutos, false)

            listaProdutos.imgListaProduto.setImageResource(R.drawable.advancewars)
            listaProdutos.tvListaProdutoTitulo.text = "Advance Wars 3"
            listaProdutos.tvListaProdutoPlataforma.text = "Nintendo DS"
            listaProdutos.tvListaProdutoPreco.text = "R$180,00"

            fragmento.containerListaProdutos.addView(listaProdutos)

            // Colocar click para abrir, ver se é possivel colocar na imagem, link ou se precisa ter uma botão no layout para isso
        }


        return fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance(origem: String): listProdFrag {
            val frag = listProdFrag()

            val bundle = Bundle().apply {
                putString("origem_key", origem)
            }

            frag.arguments = bundle

            return frag
        }
    }
}