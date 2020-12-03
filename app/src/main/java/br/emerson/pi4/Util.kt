package br.emerson.pi4

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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

    fun criarSessao(id: Int, token: String, name: String, email: String)
    {
        Sessao.UserID       = id
        Sessao.UserToken    = token
        Sessao.UserName     = name
        Sessao.UserEmail    = email
    }

    fun resetarSessao()
    {
        Sessao.UserID       = 0
        Sessao.UserToken    = ""
        Sessao.UserName     = ""
        Sessao.UserEmail    = ""
    }
}

fun converterBase64( imageString: String ): Bitmap
{
    var base64String = imageString

    base64String = base64String.replace("data:image/jpeg;base64,","")
    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
    val decodedImageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    return decodedImageBitmap
}

fun mascaraValor( valorString: String): String
{
    return "R$" + valorString.replace(".",",")
}
