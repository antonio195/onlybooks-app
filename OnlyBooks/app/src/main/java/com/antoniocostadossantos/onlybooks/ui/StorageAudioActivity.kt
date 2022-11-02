package com.antoniocostadossantos.onlybooks.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageAudioBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class StorageAudioActivity : AppCompatActivity() {
    lateinit var binding: ActivityStorageAudioBinding
    lateinit var audioUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectAudio.setOnClickListener {
            selectAudio()
        }

        binding.btnUploadAudio.setOnClickListener {
            uploadAudio()
        }
    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("audios/$name").downloadUrl.addOnSuccessListener {
                binding.etUrl.setText(it.toString())
            }.addOnFailureListener {
                binding.etUrl.setText(it.toString())
            }
    }

    fun selectAudio(){
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    fun uploadAudio() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("audios/$fileName")

        storageReference.putFile(audioUri).addOnSuccessListener {
            Toast.makeText(this@StorageAudioActivity, "Sucesso ao subir", Toast.LENGTH_SHORT).show()
            getURL(fileName)
        }.addOnFailureListener {
            Toast.makeText(this@StorageAudioActivity, "Falha ao subir", Toast.LENGTH_SHORT).show()
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            audioUri = data?.data!!
        }
    }
}