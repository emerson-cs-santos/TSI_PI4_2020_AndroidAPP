package br.emerson.pi4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.carrinho_item.view.*
import kotlinx.android.synthetic.main.fragment_carrinho.*
import kotlinx.android.synthetic.main.fragment_carrinho.view.*

class CarrinhoFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val fragmento = inflater.inflate(R.layout.fragment_carrinho, container, false)

        fragmento.tvCarrinhototal.text = "Total: R$645,00"

        for ( x in 0..5 )
        {
            val carrinhoItem =  inflater.inflate(R.layout.carrinho_item, containerCarrinho, false)

            carrinhoItem.imgCarrinhoItem.setImageResource(R.drawable.bleach_blade_battlers_2)
            carrinhoItem.tvCarrinhoItemTitulo.text = "Bleach Blade Battlers 2 - " + x
            carrinhoItem.tvCarrinhoItemPlataforma.text = "Playstation 2"
            carrinhoItem.tvCarrinhoItemPreco.text = "R$129,00"
            carrinhoItem.tvCarrinhoItemSubTotal.text = "R$129,00"
            carrinhoItem.tvCarrinhoItemQuantidade.text = "1"

            // Botões de diminuir e aumentar vão fazer 3 requisições
                // Aumentar ou diminuir (esse diminuir exclui aleatoriamente 1 item dess produto do carrinho)
                    // Aumentar api já atende - É incluso mais 1 registro desse produto com quantidade 1
                    // Diminuir 1 - api já atende - É deletado 1 registro desse produto da tabela de carrinho
                    // Remover item - api já atende - Deleta todos os registro desse produto da tabela de carrinho

                // Fazer requisição para carregar informações só de 1 produto do carrinho, para atualizar subtotal
                    // Carregar informações de 1 item do carrinho - api já atende

                // Fazer mais uma requisição para carregar novo total
                    // Carregar total do carrinho api já atende



            // Quando clicar em remover item e só tiver 1, precisa tirar o item do carrinho das views
            carrinhoItem.btnCarrinhoItemDiminuir.setOnClickListener {
                fragmento.containerCarrinho.removeView(carrinhoItem)
                activity?.let { it1 -> avisoPosAcao(it1, "Item removido") }
            }

            carrinhoItem.tvCarrinhoItemRemover.setOnClickListener {
                fragmento.containerCarrinho.removeView(carrinhoItem)
                activity?.let { it1 -> avisoPosAcao(it1, "Item removido") }
            }

            fragmento.containerCarrinho.addView(carrinhoItem)
        }

        fragmento.btnCarrinhoFinalizar.setOnClickListener {
            activity?.let { it1 -> alert("Pedido Finalizado!", "Pedido numero X gerado, muito obrigado!", it1) }
        }

        return fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance() = CarrinhoFragment()
    }
}