package com.example.engetech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPF : Button = findViewById(R.id.buttonPF)
        val buttonPJ : Button = findViewById(R.id.buttonPJ)

        buttonPF.setOnClickListener{
            telaPessoaFisica()
        }

        buttonPJ.setOnClickListener{
            telaPessoaJuridica()
        }

    }

    private fun telaPessoaFisica(){
        val telaPF = Intent(this,PessoaFisica::class.java)
        startActivity(telaPF)
    }

    private fun telaPessoaJuridica(){
        val telaPJ = Intent(this,PessoaJuridica::class.java)
        startActivity(telaPJ)
    }

}