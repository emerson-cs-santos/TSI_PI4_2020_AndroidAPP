package br.emerson.pi4

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun alert (title: String, msg: String, Context: Context) {
    val builder = AlertDialog. Builder(Context)

    builder
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton("OK",null)
        .create()
        .show()
}

fun avisoPosAcao(Context: Context, msg: String)
{
    Toast.makeText(Context, msg, Toast.LENGTH_LONG).show()
}

class controleSessao
{
    fun validarSessao(): Boolean
    {
        return Sessao.UserID == 0
    }

    fun criarSessao(): Boolean
    {
        Sessao.UserID = 56

        return true
    }

}