package br.emerson.pi4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_categoria.*
import kotlinx.android.synthetic.main.fragment_categoria.view.*
import kotlinx.android.synthetic.main.lista_categoria.view.*

class CategoriaFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val fragmento =  inflater.inflate(R.layout.fragment_categoria, container, false)

        for ( x in 0..10 )
        {
            val listaCategoria = inflater.inflate(R.layout.lista_categoria, containerListaCategoria, false )

            listaCategoria.btnListaCategoria.text = "Playstation " + x

            listaCategoria.btnListaCategoria.setOnClickListener {
                val frag = ProdutoListaFragment.newInstance( "categoria", "Playstation " + x, 0)
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragContainer, frag)?.commit()
            }

            fragmento.containerListaCategoria.addView(listaCategoria)
        }

        return fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriaFragment()
    }
}