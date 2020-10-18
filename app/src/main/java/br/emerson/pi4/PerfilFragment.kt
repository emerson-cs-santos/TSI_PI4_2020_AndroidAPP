package br.emerson.pi4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import kotlinx.android.synthetic.main.perfil_pedidos.view.*

class PerfilFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val fragmento = inflater.inflate(R.layout.fragment_perfil, container, false)

        fragmento.tvPerfilUsuarioNome.text = "Zero"

        for ( x in 0..10 )
        {
            val pedido =  inflater.inflate(R.layout.perfil_pedidos, containerPerfilUsuario, false)

            pedido.tvPerfilPedidosNroPedido.text = "125 - " + x
            pedido.tvPerfilPedidosValor.text = "R$258,00"
            pedido.tvPerfilPedidosData.text = "18/10/2020"

            fragmento.containerPerfilUsuario.addView(pedido)
        }

        return fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance() = PerfilFragment()
    }
}