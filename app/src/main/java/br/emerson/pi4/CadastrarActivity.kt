package br.emerson.pi4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.emerson.pi4.Model.CriarCadastro
import br.emerson.pi4.Model.Resposta
import kotlinx.android.synthetic.main.activity_cadastrar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)

        btnCadastrarCriarCadastro.setOnClickListener {

            if ( etCadastrarNome.text.isEmpty() || etCadastrarEmail.text.isEmpty() || etCadastrarSenha.text.isEmpty() || etCadastrarSenhaConfirmar.text.isEmpty() )
            {
                alert(getString(R.string.problemas), getString(R.string.camposObrigatorios), this)
            }
            else
            {
                val novoUser = CriarCadastro(
                    etCadastrarNome.text.toString()
                    ,etCadastrarEmail.text.toString()
                    ,etCadastrarSenha.text.toString()
                    ,etCadastrarSenhaConfirmar.text.toString()
                )

                // Inicia retrofit com a URL base
                val retrofit = RetrofitBase().base
                val userService = retrofit.create( br.emerson.pi4.Service.CadastroService::class.java )
                var criarUSerServiceCall = userService.addUsuario( novoUser )

                val criarUserCallBack = object : Callback<Resposta>
                {
                    override fun onResponse(call: Call<Resposta>, response: Response<Resposta>)
                    {
                        if ( response.isSuccessful )
                        {
                            val criarUserRetorno = response.body()

                            if ( criarUserRetorno!!.success == "true" )
                            {
                                avisoPosAcao(this@CadastrarActivity, getString(R.string.usuarioCriado) )
                                finish()
                            }
                            else
                            {
                                alert( getString(R.string.problemas), criarUserRetorno!!.message, this@CadastrarActivity )
                            }
                        }
                        else
                        {
                          //  val criarUserRetorno = response.message()
                         //   avisoPosAcao(this@CadastrarActivity, criarUserRetorno!!.message )
                         //   avisoPosAcao(this@CadastrarActivity, getString(R.string.apiResposta) )
                            alert(getString(R.string.problemas), getString(R.string.apiResposta), this@CadastrarActivity)
                            Log.e( response.code().toString(), response.errorBody().toString() )
                        }
                    }

                    override fun onFailure(call: Call<Resposta>, t: Throwable) {
                        avisoPosAcao(this@CadastrarActivity, getString(R.string.apiRespostaFalha))
                        Log.e( "CadastrarActivity", "btnCadastrarCriarCadastro", t )
                    }
                }
                criarUSerServiceCall.enqueue( criarUserCallBack )
            }
        }
    }
}