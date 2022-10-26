package com.antoniocostadossantos.onlybooks.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageImageBinding
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class StorageImageActivity : AppCompatActivity() {
    lateinit var binding: ActivityStorageImageBinding
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBannerEbook.setImageURI(null)

        binding.btnSelectedImage.setOnClickListener{
            selectImage()
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImage()
        }
    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("images/$name").downloadUrl.addOnSuccessListener {
                binding.etUrl.setText(it.toString())
                Glide.with(this).load(it.toString()).into(binding.ivBannerEbook)
            }.addOnFailureListener {
                binding.etUrl.setText(it.toString())
            }
    }

    fun selectImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    fun uploadImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this@StorageImageActivity, "Sucesso ao subir", Toast.LENGTH_SHORT).show()
            getURL(fileName)
        }.addOnFailureListener {
            Toast.makeText(this@StorageImageActivity, "Falha ao subir", Toast.LENGTH_SHORT).show()
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            imageUri = data?.data!!
            binding.ivBannerEbook.setImageURI(imageUri)
        }
    }
}