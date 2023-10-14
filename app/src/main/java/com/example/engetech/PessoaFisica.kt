package com.example.engetech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.CheckBox
import android.widget.Toast


class PessoaFisica : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private var isChecked1: Boolean = false
    private var isChecked2: Boolean = false
    private var isChecked3: Boolean = false

    private var isChecked1T: Boolean = false
    private var isChecked2T: Boolean = false
    private var isChecked3T: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa_fisica)

        val buttonSalvar: Button = findViewById(R.id.btn_salvar)

        val check_plano: CheckBox = findViewById(R.id.check_terreno_plano)
        val check_aclive: CheckBox = findViewById(R.id.check_terreno_aclive)
        val check_declive: CheckBox = findViewById(R.id.check_terreno_declive)

        val check_casa_cond: CheckBox = findViewById(R.id.check_casaCond)
        val check_apartamento: CheckBox = findViewById(R.id.check_apartamento)
        val check_casa: CheckBox = findViewById(R.id.check_casa)


        val nomeTextField: TextInputLayout = findViewById(R.id.nome)
        val cpfTextField: TextInputLayout = findViewById(R.id.cpf)
        val emailTextField: TextInputLayout = findViewById(R.id.email)
        val telefoneTextField: TextInputLayout = findViewById(R.id.telefone)
        val enderecoTextField: TextInputLayout = findViewById(R.id.endereco)
        val cidadeTextField: TextInputLayout = findViewById(R.id.cidade)
        val numSinistroTextField: TextInputLayout = findViewById(R.id.numSinistro)

        val nomeEditText: EditText? = nomeTextField.editText
        val cpfEditText: EditText? = cpfTextField.editText
        val emailEditText: EditText? = emailTextField.editText
        val telefoneEditText: EditText? = telefoneTextField.editText
        val enderecoEditText: EditText? = enderecoTextField.editText
        val cidadeEditText: EditText? = cidadeTextField.editText
        val numSinistroEditText: EditText? = numSinistroTextField.editText

        buttonSalvar.setOnClickListener{

            val nome = nomeEditText?.text.toString()
            val cpf = cpfEditText?.text.toString()
            val email = emailEditText?.text.toString()
            val telefone = telefoneEditText?.text.toString()
            val endereco = enderecoEditText?.text.toString()
            val cidade = cidadeEditText?.text.toString()
            val numSinistro = numSinistroEditText?.text.toString()

            val tipo_residencia = verifica_residencia(check_casa_cond, check_apartamento, check_casa)
            val tipo_terreno = verifica_terreno(check_plano,check_aclive,check_declive)

            val usuarioMap = hashMapOf(
                "nome" to nome,
                "cpf" to cpf,
                "email" to email,
                "telefone" to telefone,
                "endereco" to endereco,
                "cidade" to cidade,
                "numero_sinistro" to numSinistro,
                "tipo_residencia" to tipo_residencia,
                "tipo_terreno" to tipo_terreno
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

    private fun verifica_residencia(check_casa_cond:CheckBox,check_apartamento:CheckBox,check_casa:CheckBox): String{
            isChecked1 = check_casa_cond.isChecked
            isChecked2 = check_apartamento.isChecked
            isChecked3 = check_casa.isChecked

            if (isChecked1 == true){
                val tipo_resi = check_casa_cond.text.toString()
                return tipo_resi
            }else if (isChecked2 == true){
                val tipo_resi = check_apartamento.text.toString()
                return tipo_resi
            }else{
                val tipo_resi = check_casa.text.toString()
                return tipo_resi
            }

    }

    private fun verifica_terreno(check_plano:CheckBox,check_aclive:CheckBox,check_declive:CheckBox): String{
        isChecked1T = check_plano.isChecked
        isChecked2T = check_aclive.isChecked
        isChecked3T = check_declive.isChecked

        if (isChecked1T == true){
            val tipo_terreno = check_plano.text.toString()
            return tipo_terreno
        }else if (isChecked2T == true){
            val tipo_terreno = check_aclive.text.toString()
            return tipo_terreno
        }else{
            val tipo_terreno = check_declive.text.toString()
            return tipo_terreno
        }


    }


















}
