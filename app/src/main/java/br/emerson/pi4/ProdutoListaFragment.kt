package br.emerson.pi4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_prod.*
import kotlinx.android.synthetic.main.fragment_list_prod.view.*
import kotlinx.android.synthetic.main.lista_produto.view.*

class ProdutoListaFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_list_prod, container, false)

        val origemChamada = arguments?.getString("origem_key")
        val descricaoChamada = arguments?.getString("origemDesc")
        val origemID = arguments?.getInt("origemID")

        // Tipos de origemChamada:
            // main
                //  vem do FragMain, ao escolher a opção de jogos, deve ser exibido todos os jogos

            // lancamentos
                // Vem da opção ver todos da tela principal

            // maisvendidos
                // Vem da opção todos da tela principal

            // categoria
                // Produtos da categoria, usar a varivavel origemID, pois é o ID da categoria

            // itenspedido
                // Produtos de um pedido, usar a varivavel origemID, pois é o ID do Pedido


        fragmento.tvListagemProdutos.text = descricaoChamada

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