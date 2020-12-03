package br.emerson.pi4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.emerson.pi4.Model.Pedido
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import kotlinx.android.synthetic.main.perfil_pedidos.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilFragment : Fragment() {

    val retrofit = RetrofitBase().base
    val pedidoService = retrofit.create( br.emerson.pi4.Service.PedidoService::class.java )

    override fun onResume() {
        if( controleSessao().validarSessao() )
        {
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }
        else
        {
            tvPerfilUsuarioNome.text = Sessao.UserName
            carregarPedidos()
        }
        super.onResume()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val fragmento = inflater.inflate(R.layout.fragment_perfil, container, false)

        fragmento.tvPerfilUsuarioNome.text = Sessao.UserName

        fragmento.btnPerfilUsuarioEditarPerfil.setOnClickListener {
            val i = Intent(activity, EditarUsuarioActivity::class.java)
            startActivity(i)
        }

        fragmento.btnPerfilUsuarioTrocarSenha.setOnClickListener {
            val i = Intent(activity, TrocarSenhaActivity::class.java)
            startActivity(i)
        }

        return fragmento
    }

    fun carregarPedidos()
    {
        progressBarPerfil.visibility = View.VISIBLE

        containerPerfilUsuario.removeAllViews()

        val pedidoServiceCall = pedidoService.listPedidos( "Bearer " + Sessao.UserToken, Sessao.UserID )

        val pedidoCallBack = object : Callback<List<Pedido>>
        {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {

                if ( response.isSuccessful )
                {
                    val pedidoRetorno = response.body()

                    pedidoRetorno?.let {
                        for (pedidoRetorno in it) {

                            val pedido = this@PerfilFragment.layoutInflater.inflate(R.layout.perfil_pedidos, containerPerfilUsuario, false)

                            pedido.tvPerfilPedidosNroPedido.text = pedidoRetorno.id.toString()
                            pedido.tvPerfilPedidosValor.text = pedidoRetorno.total
                            pedido.tvPerfilPedidosData.text = pedidoRetorno.data

                            pedido.tvPerfilPedidosVerItens.setOnClickListener {
                                val i = Intent(activity, ItemPedidoActivity::class.java )
                                i.putExtra("PedidoID", pedidoRetorno.id.toString() )
                                i.putExtra("PedidoTotal", pedidoRetorno.total )
                                startActivity(i)
                            }

                            containerPerfilUsuario.addView( pedido )
                        }
                        progressBarPerfil.visibility = View.GONE
                    }
                }
                else
                {
                    Log.e(response.code().toString(), response.errorBody().toString() )
                    activity?.let { avisoPosAcao(it, getString(R.string.loginExpirou) ) }
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                activity?.let { avisoPosAcao(it, getString(R.string.apiRespostaFalha) ) }
                Log.e( "PerfilFragment", "carregarPedidos", t )
            }
        }
        pedidoServiceCall.enqueue( pedidoCallBack )
    }

    companion object {
        @JvmStatic
        fun newInstance() = PerfilFragment()
    }
}