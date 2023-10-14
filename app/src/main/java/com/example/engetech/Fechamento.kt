package com.example.engetech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class Fechamento : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private var isChecked1: Boolean = false
    private var isChecked2: Boolean = false
    private var isChecked3: Boolean = false
    private var isChecked4: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fechamento)

        val buttonSalvar : Button = findViewById(R.id.btn_salvar_fechamento)

        val check_ceramica: CheckBox = findViewById(R.id.check_ceramica)
        val check_tijolos: CheckBox = findViewById(R.id.check_tijolos)
        val check_drywall: CheckBox = findViewById(R.id.check_drywall)
        val check_madeira: CheckBox = findViewById(R.id.check_madeira)

        buttonSalvar.setOnClickListener{
            val fechamento = verifica_fechamento(check_ceramica,check_tijolos,check_drywall,check_madeira)

            val usuarioMap = hashMapOf(
                "fechamento" to fechamento
            )

            db.collection("Fechamento").document("FECHA ")
                .set(usuarioMap).addOnCompleteListener {
                    Toast.makeText(this, "Sucesso ao salvar os dados", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Log.d("db", "Erro ao salvar os dados")
                }

            telaMenu()
        }
    }
    private fun telaMenu(){
        val menu = Intent(this,MenuPrincipal::class.java)
        startActivity(menu)
    }

    private fun verifica_fechamento(check_ceramica:CheckBox,check_tijolos:CheckBox,check_drywall:CheckBox,check_madeira:CheckBox): String{
        isChecked1 = check_ceramica.isChecked
        isChecked2 = check_tijolos.isChecked
        isChecked3 = check_drywall.isChecked
        isChecked4 = check_madeira.isChecked

        if (isChecked1 == true){
            val fechamento = check_ceramica.text.toString()
            return fechamento
        }else if (isChecked2 == true){
            val fechamento = check_tijolos.text.toString()
            return fechamento
        }else if (isChecked3 == true) {
            val fechamento = check_drywall.text.toString()
            return fechamento
        }
        else{
            val fechamento = check_madeira.text.toString()
            return fechamento
        }
    }
}