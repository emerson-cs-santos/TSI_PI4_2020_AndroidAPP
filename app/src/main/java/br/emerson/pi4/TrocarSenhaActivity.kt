package br.emerson.pi4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.emerson.pi4.Model.AtualizarSenha
import br.emerson.pi4.Model.Resposta
import kotlinx.android.synthetic.main.activity_trocar_senha.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrocarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trocar_senha)

        btnTrocarSenhaSalvar.setOnClickListener {

            if ( etTrocarSenhaAntigaSenha.text.isEmpty() || etTrocarSenhaNovaSenha.text.isEmpty() || etTrocarSenhaConfirmarNovaSenha.text.isEmpty() )
            {
                alert(getString(R.string.problemas), getString(R.string.camposObrigatorios), this)
            }
            else
            {
                val alterarSenha = AtualizarSenha(
                    etTrocarSenhaAntigaSenha.text.toString()
                    ,etTrocarSenhaNovaSenha.text.toString()
                    ,etTrocarSenhaConfirmarNovaSenha.text.toString()
                )

                // Inicia retrofit com a URL base
                val retrofit = RetrofitBase().base
                val userService = retrofit.create( br.emerson.pi4.Service.CadastroService::class.java )
                var alterarSenhaServiceCall = userService.atualizarSenha ( "Bearer " + Sessao.UserToken, Sessao.UserID, alterarSenha )

                val alterarSenhaCallBack = object : Callback<Resposta>
                {
                    override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {

                        if ( response.isSuccessful )
                        {
                            val alterarSenhaRetorno = response.body()

                            if ( alterarSenhaRetorno!!.success == "true" )
                            {
                                avisoPosAcao(this@TrocarSenhaActivity, getString(R.string.atualizadoSucesso) )
                                finish()
                            }
                            else
                            {
                                alert( getString(R.string.problemas), alterarSenhaRetorno!!.message, this@TrocarSenhaActivity )
                            }
                        }
                        else
                        {
                            Log.e(response.code().toString(), response.errorBody().toString())
                            controleSessao().resetarSessao()
                            avisoPosAcao(this@TrocarSenhaActivity, getString(R.string.loginExpirou))
                            finish()
                        }

                    }

                    override fun onFailure(call: Call<Resposta>, t: Throwable) {
                        avisoPosAcao(this@TrocarSenhaActivity, getString(R.string.apiRespostaFalha))
                        Log.e( "TrocarSenhaActivity", "btnTrocarSenhaSalvar", t )
                    }
                }
                alterarSenhaServiceCall.enqueue( alterarSenhaCallBack )
            }
        }
    }
}