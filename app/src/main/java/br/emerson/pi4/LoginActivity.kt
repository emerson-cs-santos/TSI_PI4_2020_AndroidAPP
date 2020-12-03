package br.emerson.pi4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.emerson.pi4.Model.Login
import br.emerson.pi4.Model.Resposta
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvLoginCadastrar.setOnClickListener {
            val i = Intent(this, CadastrarActivity::class.java)
            startActivity(i)
        }

        btnLoginEntrar.setOnClickListener {
            if ( etLoginEmail.text.isEmpty() || etLoginSenha.text.isEmpty() )
            {
                alert(getString(R.string.problemas), getString(R.string.camposObrigatorios), this)
            }
            else
            {
                val loginDados = Login(
                    etLoginEmail.text.toString()
                    ,etLoginSenha.text.toString()
                )

                // Inicia retrofit com a URL base
                val retrofit = RetrofitBase().base
                val userService = retrofit.create( br.emerson.pi4.Service.CadastroService::class.java )
                var loginUserServiceCall = userService.login( loginDados )

                val loginUserCallBack = object : Callback<Resposta>
                {
                    override fun onResponse(call: Call<Resposta>, response: Response<Resposta>)
                    {
                        if ( response.isSuccessful )
                        {
                            val loginUserRetorno = response.body()

                            val usuarioID       = loginUserRetorno!!.user_id
                            val usuarioToken    = loginUserRetorno!!.token
                            val usuarioName     = loginUserRetorno.message
                            var usuarioEmail    = loginUserRetorno.success

                            controleSessao().criarSessao(usuarioID!!, usuarioToken!!, usuarioName, usuarioEmail)

                            avisoPosAcao(this@LoginActivity, getString(R.string.bemVindo) )
                            finish()
                        }
                        else
                        {
                            alert(getString(R.string.problemas), getString(R.string.apiResposta), this@LoginActivity)
                            Log.e( response.code().toString(), response.errorBody().toString() )
                        }
                    }

                    override fun onFailure(call: Call<Resposta>, t: Throwable) {
                        avisoPosAcao(this@LoginActivity, getString(R.string.apiRespostaFalha))
                        Log.e( "LoginActivity", "onCreate", t )
                    }
                }
                loginUserServiceCall.enqueue( loginUserCallBack )
            }
        }
    }
}