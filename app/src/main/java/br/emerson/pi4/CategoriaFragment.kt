package br.emerson.pi4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.emerson.pi4.Model.Categoria
import br.emerson.pi4.Service.CategoriaService
import kotlinx.android.synthetic.main.fragment_categoria.*
import kotlinx.android.synthetic.main.fragment_categoria.view.*
import kotlinx.android.synthetic.main.lista_categoria.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaFragment : Fragment() {

    // Inicia retrofit com a URL base
    val retrofit = RetrofitBase().base

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val fragmento =  inflater.inflate(R.layout.fragment_categoria, container, false)

        val CategoriaService = retrofit.create( CategoriaService::class.java )
        val CategoriaCall = CategoriaService.list()

        val CategoriaCallBack = object : Callback<List<Categoria>>
        {
            override fun onResponse( call: Call<List<Categoria>>, response: Response<List<Categoria>> )
            {
                if ( response.isSuccessful )
                {
                    val listaCategoria = response.body()

                    fragmento.containerListaCategoria.removeAllViews()

                    listaCategoria?.let {
                        for ( listaCategoria in it )
                        {
                            val catLayout =  inflater.inflate(R.layout.lista_categoria, containerListaCategoria, false)

                            catLayout.btnListaCategoria.text = listaCategoria.name

                            catLayout.btnListaCategoria.setOnClickListener {
                                val frag = ProdutoListaFragment.newInstance( "categoria", listaCategoria.name, listaCategoria.id)
                                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragContainer, frag)?.commit()
                            }

                            fragmento.containerListaCategoria.addView( catLayout )
                        }
                        fragmento.progressBarListaCategoria.visibility = View.GONE
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
                Log.e( "CategoriaListaActivity", "getCategorias", t )
            }

        }

        CategoriaCall.enqueue( CategoriaCallBack )

   //     for ( x in 0..10 )
    //    {
   //         val listaCategoria = inflater.inflate(R.layout.lista_categoria, containerListaCategoria, false )

      //      listaCategoria.btnListaCategoria.text = "Playstation " + x

     //       listaCategoria.btnListaCategoria.setOnClickListener {
     //           val frag = ProdutoListaFragment.newInstance( "categoria", "Playstation " + x, 0)
      //          activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragContainer, frag)?.commit()
     //       }

     //       fragmento.containerListaCategoria.addView(listaCategoria)
      //  }

        return fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriaFragment()
    }
}