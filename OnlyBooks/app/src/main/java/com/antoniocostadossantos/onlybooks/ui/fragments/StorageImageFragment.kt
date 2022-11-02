package com.antoniocostadossantos.onlybooks.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageImageBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class StorageImageFragment(val ebook: EbookModel) : Fragment() {
    lateinit var binding: ActivityStorageImageBinding
    lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityStorageImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupImageEbook(ebook)
        displayData()

        binding.selectImage.setOnClickListener {
            selectImage()
        }

        binding.uploadImage.setOnClickListener {
            uploadImage()
        }
    }

    private fun displayData() {
        binding.etUrl.setText(ebook.nameEbook)

    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("images/$name").downloadUrl.addOnSuccessListener {
                Glide.with(this).load(it.toString()).into(binding.ivBannerEbook)
            }.addOnFailureListener {
                binding.etUrl.setText(it.toString())
            }
    }

    fun selectImage() {
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
            Toast.makeText((context as FragmentActivity), "Sucesso ao subir", Toast.LENGTH_SHORT)
                .show()
            getURL(fileName)
        }.addOnFailureListener {
            Toast.makeText((context as FragmentActivity), "Falha ao subir", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupImageEbook(ebook: EbookModel) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(binding.ivBannerEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebook.url)
            .into(binding.ivBannerEbook)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                imageUri = data?.data!!
                binding.ivBannerEbook.setImageURI(imageUri)
            }
        }
}