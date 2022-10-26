package com.antoniocostadossantos.onlybooks.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageFileBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class StorageFileActivity : AppCompatActivity() {
    lateinit var binding: ActivityStorageFileBinding
    lateinit var fileUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectedFile.setOnClickListener {
            selectFile()
        }

        binding.btnUploadFile.setOnClickListener {
            uploadFile()
        }
    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("documents/$name").downloadUrl.addOnSuccessListener {
                binding.etUrl.setText(it.toString())
            }.addOnFailureListener {
                binding.etUrl.setText(it.toString())
            }
    }

    fun downloadFromName(name: String): String {
        var path = ""
        var error = ""
        FirebaseStorage.getInstance()
            .getReference("documents/$name").downloadUrl.addOnSuccessListener {
                path = it.path!!
            }.addOnFailureListener {
                error = it.toString()
            }

        if (!path.isNullOrEmpty()) {
            return path
        }
        return error
    }

    fun selectFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    fun uploadFile() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("documents/$fileName")

        storageReference.putFile(fileUri).addOnSuccessListener {
            Toast.makeText(this@StorageFileActivity, "Sucesso ao subir", Toast.LENGTH_SHORT).show()
            getURL(fileName)
        }.addOnFailureListener {
            Toast.makeText(this@StorageFileActivity, "Falha ao subir", Toast.LENGTH_SHORT).show()
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                fileUri = data?.data!!
            }
        }
}