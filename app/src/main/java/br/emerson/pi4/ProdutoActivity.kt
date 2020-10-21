package br.emerson.pi4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_produto.*


class ProdutoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

    //    var base64String: String = getString(R.string.teste)
        //  imgProduto.setImageResource(R.drawable.bleach_blade_battlers_2)

        // Tentativa 7
      // val base64Image = base64String.split(",".toRegex()).toTypedArray()[1]
       //val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
        //val decodedByte =  BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        // Tentativa 8
      //  var decodedString: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
       // var decodedByte: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        // Tentativa 9
        //val image64: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        //val decodedByte: Bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.size)

      //  imgProduto.setImageBitmap( decodedByte )

        // Tentativa 10
    //    val image64: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
    //    val bmp = BitmapFactory.decodeByteArray(image64, 0, base64String.length)

     //   imgProduto.setImageBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false));


        // Tentativa 11
   //    base64String = base64String.replace("data:image/jpeg;base64,","");

        //val image64: ByteArray = Base64.decode(base64String, 0)
      // val teste: Bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.size)
       // imgProduto.setImageBitmap(
       //     BitmapFactory.decodeByteArray(image64, 0, image64.size)
     //   )

      //  val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
     //   val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    //    imgProduto.setImageBitmap(decodedImage)




    }
}