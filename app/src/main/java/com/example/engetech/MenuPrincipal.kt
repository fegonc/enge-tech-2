package com.example.engetech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.engetech.databinding.ActivityMenuPrincipalBinding
import android.content.Intent

class MenuPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityMenuPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icNavegarFechamento.setOnClickListener{
            val navegar_tela_fechamento = Intent(this, Fechamento::class.java)
            startActivity(navegar_tela_fechamento)
        }

        binding.icNavegarPiso.setOnClickListener{
            val navegar_tela_piso = Intent(this, InstalacaoPiso::class.java)
            startActivity(navegar_tela_piso)
        }
    }
}