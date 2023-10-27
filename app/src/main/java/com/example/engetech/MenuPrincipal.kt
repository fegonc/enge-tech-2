package com.example.engetech

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.engetech.databinding.ActivityMenuPrincipalBinding
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileOutputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

import android.widget.ImageView
import java.util.concurrent.Executors


class MenuPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityMenuPrincipalBinding

    private val db = FirebaseFirestore.getInstance()
    private val data = mutableListOf<Map<String, Any>>()
    private val listaVaziaMutavel = mutableListOf<String>()

    private val REQUEST_CODE = 1232

    val collection1 = db.collection("Cadastro")
    val collection2 = db.collection("Fechamento")
    val collection3 = db.collection("Insta_Piso")
    val collection4 = db.collection("images")

    val task1 = collection1.get()
    val task2 = collection2.get()
    val task3 = collection3.get()
    val task4 = collection4.get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askPermission()

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

        binding.btnRelatorio.setOnClickListener{
                Tasks.whenAllComplete(task1, task2, task3, task4).addOnCompleteListener { taskSnapshot ->
                    if (taskSnapshot.isSuccessful) {
                        for (document in task1.result) {
                            val item = document.data
                            data.add(item)
                        }
                        for (document in task2.result) {
                            val item = document.data
                            data.add(item)
                        }
                        for (document in task3.result) {
                            val item = document.data
                            data.add(item)
                        }
                        for (document in task3.result) {
                            val item = document.data
                            data.add(item)
                        }
                        for (document in task4.result) {
                            val imagePath = document.getString("pic")
                            if (imagePath != null) {
                                listaVaziaMutavel.add(imagePath)
                            }
                         }

                        createPDF(data)

                    } else {
                        // Lida com possíveis erros
                    }
                }

        }

    }

    private fun askPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            ), REQUEST_CODE)
    }

    private fun createPDF(data: List<Map<String, Any>>){
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(1080,1920,1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint().apply {
            color = Color.BLACK // Mude a cor do texto para preto
            textSize = 32f // Aumente o tamanho da fonte
        }

        val backgroundPaint = Paint().apply {
            color = Color.YELLOW // Defina uma cor de fundo para destacar o texto
        }

        val dataInfo = mutableMapOf<String, String>()
        for (collection in data){
            for (item in collection){
                val chave: String = item.key.toString()
                val valor: String = item.value.toString()
                dataInfo[chave] = valor
            }
        }

        val x = 100f
        var y = 100f

        for ((key, value) in dataInfo) {
            // Desenhe um retângulo de fundo
            canvas.drawRect(x - 10f, y - 30f, x + 600f, y + 10f, backgroundPaint)

            // Desenhe o texto em cima do retângulo
            canvas.drawText("$key: $value", x, y, paint)
            y += 60 // Espaçamento entre os itens

        }
        val bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.house)
        val scale = Bitmap.createScaledBitmap(bitmap, 500, 500, false)
        canvas.drawBitmap(scale, x, y, null)

        y+=520

        val bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.house2)
        val scale2 = Bitmap.createScaledBitmap(bitmap2, 500, 500, false)
        canvas.drawBitmap(scale2, x, y, null)



//        for (imagePath in images) {
//            val bitmap = BitmapFactory.decodeFile(imagePath)
//            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, false)
//            canvas.drawBitmap(scaledBitmap, x, y, null)
//            y += 600 // Ajuste o espaçamento das imagens conforme necessário
//        }

        document.finishPage(page)

        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "engetech.pdf"
        val file = File(downloadDir, fileName)
        val fos = FileOutputStream(file)
        document.writeTo(fos)
        document.close()
        fos.close()
        Toast.makeText(this, "Dados gravados com Sucesso", Toast.LENGTH_SHORT).show()

    }


}
