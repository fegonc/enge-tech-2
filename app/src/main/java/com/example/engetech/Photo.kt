package com.example.engetech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

@Suppress("DEPRECATION")
class Photo : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageView: ImageView

    private lateinit var button2: Button
    private lateinit var imageView2: ImageView

    var verificaCondicao: Boolean = false

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo2)

        button = findViewById(R.id.btn_foto)
        imageView = findViewById(R.id.img_view_foto1)

        button2 = findViewById(R.id.btn_foto2)
        imageView2 = findViewById(R.id.img_view_foto2)

        button.setOnClickListener{
            pegarImagemGaleria()
        }
        button2.setOnClickListener{
            pegarImagemGaleria2()
        }

        val buttonSalvar : Button = findViewById(R.id.btn_salvar_photo)
        buttonSalvar.setOnClickListener{
            tela_menu_principal()
        }

    }


    private fun pegarImagemGaleria(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
        verificaCondicao = true
    }
    private fun pegarImagemGaleria2(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
        verificaCondicao = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && verificaCondicao){
            imageView.setImageURI(data?.data)
        }
        else
            imageView2.setImageURI(data?.data)

    }

    //Funcao pra chamar tela de menu principal
    private fun tela_menu_principal(){
        val menu_principal = Intent(this,MenuPrincipal::class.java)
        startActivity(menu_principal)
    }


}