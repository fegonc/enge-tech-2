package com.example.engetech

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.engetech.databinding.ActivityPhoto2Binding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Suppress("DEPRECATION")
class Photo : AppCompatActivity() {

    private lateinit var binding : ActivityPhoto2Binding
    private lateinit var storageRef : StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var imageUri1: Uri? = null
    private var imageUri2: Uri? = null
    private lateinit var imgViewFoto1: ImageView
    private lateinit var imgViewFoto2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhoto2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        registerClickEvents()
    }

    private fun registerClickEvents() {
        binding.btnSalvarPhoto.setOnClickListener {
            uploadImage(imageUri1)
            uploadImage(imageUri2)
            startActivity(Intent(this, MenuPrincipal::class.java))
        }

        binding.imgViewFoto1.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        // Assuming you have another ImageView for the second image
        binding.imgViewFoto2.setOnClickListener {
            resultLauncher2.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        imageUri1 = it
        imgViewFoto1.setImageURI(it)
    }

    // Create a second launcher for the second image
    private val resultLauncher2 = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        imageUri2 = it
        imgViewFoto2.setImageURI(it)
    }

    private fun initVars() {
        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFirestore = FirebaseFirestore.getInstance()
        imgViewFoto1 = binding.imgViewFoto1
        imgViewFoto2 = binding.imgViewFoto2
    }

    private fun uploadImage(imageUri: Uri?) {
        binding.progressBar.visibility = View.VISIBLE
        if (imageUri != null) {
            val storageRef = storageRef.child(System.currentTimeMillis().toString())
            storageRef.putFile(imageUri).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()

                        firebaseFirestore.collection("images").add(map).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful) {
                                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            binding.progressBar.visibility = View.GONE
                            imgViewFoto1.setImageResource(R.drawable.ic_launcher_foreground)
                            imgViewFoto2.setImageResource(R.drawable.ic_launcher_foreground)
                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    imgViewFoto1.setImageResource(R.drawable.ic_launcher_foreground)
                    imgViewFoto2.setImageResource(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }
}