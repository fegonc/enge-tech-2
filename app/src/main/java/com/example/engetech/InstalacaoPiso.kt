package com.example.engetech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore

class InstalacaoPiso : AppCompatActivity() {

    private var itemSelected: Any? = null
    private var itemSelectedPiso: Any? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instalacao_piso)
        val buttonSalvar : Button = findViewById(R.id.btn_salvar_piso)

        val items = listOf("Hidráulica", "Elétrica", "Lógica")
        val items_piso = listOf("Ceramico", "Porcelanato", "Madeira", "Virilico")

        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete_text)
        val autoCompletePiso : AutoCompleteTextView = findViewById(R.id.auto_complete_text_piso)

        val adapter = ArrayAdapter(this, R.layout.list_item,items)
        val adapter_piso = ArrayAdapter(this, R.layout.list_item,items_piso)

        autoComplete.setAdapter(adapter)
        autoCompletePiso.setAdapter(adapter_piso)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            itemSelected = adapterView.getItemAtPosition(i)
            //Toast.makeText(this,"Item: $itemSelected",Toast.LENGTH_LONG).show()
        }

        autoCompletePiso.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
                itemSelectedPiso = adapterView.getItemAtPosition(i)
                //Toast.makeText(this,"Item: $itemSelectedPiso",Toast.LENGTH_LONG).show()

        }

        buttonSalvar.setOnClickListener{
            itemSelected.toString()
            itemSelectedPiso.toString()

            val usuarioMap = hashMapOf(
                "instalacao" to itemSelected,
                "piso" to itemSelectedPiso
            )

            db.collection("Insta_Piso").document("IP ")
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
}