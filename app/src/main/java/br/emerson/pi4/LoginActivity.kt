package br.emerson.pi4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

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
                alert("Erro!", "Preencha todos os campos!", this)
            }
            else
            {
                controleSessao().criarSessao()
                finish()
            }
        }
    }
}