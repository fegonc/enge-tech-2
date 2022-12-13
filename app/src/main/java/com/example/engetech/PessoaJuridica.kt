package com.example.engetech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PessoaJuridica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa_juridica)

        val buttonSalvar : Button = findViewById(R.id.btn_salvar2)

        buttonSalvar.setOnClickListener{
            telaFoto()
        }

    }

    private fun telaFoto(){
        val foto = Intent(this,Photo::class.java)
        startActivity(foto)
    }

}