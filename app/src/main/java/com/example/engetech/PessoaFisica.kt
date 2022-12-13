package com.example.engetech

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PessoaFisica : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa_fisica)

        val buttonSalvar : Button = findViewById(R.id.btn_salvar)

        buttonSalvar.setOnClickListener{
            telaFoto()
        }

    }

    private fun telaFoto(){
        val foto = Intent(this,Photo::class.java)
        startActivity(foto)
    }

}
