package com.example.engetech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class PessoaJuridica : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private var isChecked1: Boolean = false
    private var isChecked2: Boolean = false
    private var isChecked3: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa_juridica)

        val buttonSalvar : Button = findViewById(R.id.btn_salvar2)

        val check_comercial: CheckBox = findViewById(R.id.check_ed_comercial)
        val check_industria: CheckBox = findViewById(R.id.check_industria)
        val check_comercio: CheckBox = findViewById(R.id.check_comercio)

        val razaoTextField: TextInputLayout = findViewById(R.id.razao_social)
        val cnpjTextField: TextInputLayout = findViewById(R.id.cnpj)
        val emailTextField: TextInputLayout = findViewById(R.id.email2)
        val telefoneTextField: TextInputLayout = findViewById(R.id.telefone2)
        val enderecoTextField: TextInputLayout = findViewById(R.id.endereco2)
        val cidadeTextField: TextInputLayout = findViewById(R.id.cidade2)
        val numSinistroTextField: TextInputLayout = findViewById(R.id.numSinistro2)
        val ocupacaoTextField: TextInputLayout = findViewById(R.id.ocupacao)

        val nomeEditText: EditText? = razaoTextField.editText
        val cpfEditText: EditText? = cnpjTextField.editText
        val emailEditText: EditText? = emailTextField.editText
        val telefoneEditText: EditText? = telefoneTextField.editText
        val enderecoEditText: EditText? = enderecoTextField.editText
        val cidadeEditText: EditText? = cidadeTextField.editText
        val numSinistroEditText: EditText? = numSinistroTextField.editText
        val ocupacaoEditText: EditText? = ocupacaoTextField.editText

        buttonSalvar.setOnClickListener{

            val razao = nomeEditText?.text.toString()
            val cnpj = cpfEditText?.text.toString()
            val email = emailEditText?.text.toString()
            val telefone = telefoneEditText?.text.toString()
            val endereco = enderecoEditText?.text.toString()
            val cidade = cidadeEditText?.text.toString()
            val numSinistro = numSinistroEditText?.text.toString()
            val ocupacao = ocupacaoEditText?.text.toString()

            val tipo_empresa = verifica_empresa(check_comercial,check_industria,check_comercio)

            val usuarioMap = hashMapOf(
                "razao_social" to razao,
                "cnpj" to cnpj,
                "email" to email,
                "telefone" to telefone,
                "endereco" to endereco,
                "cidade" to cidade,
                "numero_sinistro" to numSinistro,
                "ocupacao" to ocupacao,
                "tipo_empresa" to tipo_empresa
            )

            db.collection("Cadastro").document("PF1 ")
                .set(usuarioMap).addOnCompleteListener {
                    Toast.makeText(this, "Sucesso ao salvar os dados", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Log.d("db", "Erro ao salvar os dados")
                }


            telaFoto()
        }

    }

    private fun telaFoto(){
        val foto = Intent(this,Photo::class.java)
        startActivity(foto)
    }

    private fun verifica_empresa(check_comercial:CheckBox,check_industria:CheckBox,check_comercio:CheckBox): String{
        isChecked1 = check_comercial.isChecked
        isChecked2 = check_industria.isChecked
        isChecked3 = check_comercio.isChecked

        if (isChecked1 == true){
            val tipo_empresa = check_comercial.text.toString()
            return tipo_empresa
        }else if (isChecked2 == true){
            val tipo_empresa = check_industria.text.toString()
            return tipo_empresa
        }else{
            val tipo_empresa = check_comercio.text.toString()
            return tipo_empresa
        }

    }

}