package br.emerson.pi4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.emerson.pi4.Model.AlterarCadastro
import br.emerson.pi4.Model.Resposta
import kotlinx.android.synthetic.main.activity_editar_usuario.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)

        etEditarUsuarioNome.setText( Sessao.UserName )
        etEditarUsuarioEmail.setText(Sessao.UserEmail )

        btnEditarUsuarioSalvar.setOnClickListener {

            if ( etEditarUsuarioNome.text.isEmpty() || etEditarUsuarioEmail.text.isEmpty() )
            {
                alert(getString(R.string.problemas), getString(R.string.camposObrigatorios), this)
            }
            else
            {
                val atualizarUser = AlterarCadastro(
                    etEditarUsuarioNome.text.toString()
                    ,etEditarUsuarioEmail.text.toString()
                )

                // Inicia retrofit com a URL base
                val retrofit = RetrofitBase().base
                val userService = retrofit.create( br.emerson.pi4.Service.CadastroService::class.java )
                var atualizarUserServiceCall = userService.alterarUsuario( "Bearer " + Sessao.UserToken, Sessao.UserID, atualizarUser )

                val atualizarUserCallBack = object : Callback<Resposta>
                {
                    override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {

                        if ( response.isSuccessful )
                        {
                            val alterarUserRetorno = response.body()

                            if ( alterarUserRetorno!!.success == "true" )
                            {
                                avisoPosAcao( this@EditarUsuarioActivity, getString(R.string.atualizadoSucesso) )
                                Sessao.UserName = atualizarUser.name
                                Sessao.UserEmail = atualizarUser.email
                                finish()
                            }
                            else
                            {
                                alert( getString(R.string.problemas), alterarUserRetorno!!.message, this@EditarUsuarioActivity)
                            }
                        }
                        else
                        {
                            Log.e( response.code().toString(), response.errorBody().toString() )
                            controleSessao().resetarSessao()
                            avisoPosAcao(this@EditarUsuarioActivity, getString(R.string.loginExpirou) )
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Resposta>, t: Throwable) {
                        avisoPosAcao(this@EditarUsuarioActivity, getString(R.string.apiRespostaFalha) )
                        Log.e( "EditarUsuarioActivity", "btnEditarUsuarioSalvar", t )
                    }

                }
                atualizarUserServiceCall.enqueue( atualizarUserCallBack )
            }

        }
    }
}