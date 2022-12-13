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

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo2)

        button = findViewById(R.id.btn_foto)
        imageView = findViewById(R.id.img_view_foto1)

        button.setOnClickListener{
            pegarImagemGaleria()
        }

    }


    private fun pegarImagemGaleria(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
        }
    }





}