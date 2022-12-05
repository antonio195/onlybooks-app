package com.antoniocostadossantos.onlybooks.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageImageBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class StorageImageAudioBookFragment(val audioBookBase: AudioBookModel) : Fragment() {
    lateinit var binding: ActivityStorageImageBinding
    lateinit var imageUri: Uri
    private val ebookViewModel: EbookViewModel by viewModel()

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

        setupImageEbook(audioBookBase)

        binding.selectImage.setOnClickListener {
            selectImage()
        }

        binding.uploadImage.setOnClickListener {
            uploadImage()
        }
    }

    private fun setupImageEbook(audioBookBase: AudioBookModel) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.baixando_capa)
            .error(R.drawable.erro_capa)

        Glide.with(binding.ivBannerEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(audioBookBase.urlAudioBook)
            .into(binding.ivBannerEbook)
    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("images/$name").downloadUrl.addOnSuccessListener {
                Glide.with(this).load(it.toString()).into(binding.ivBannerEbook)
                audioBookBase.urlAudioBook = it.toString()
//                updateEbook(audioBookBase)
            }.addOnFailureListener {
            }
    }

//    private fun updateEbook(audioBookBase: AudioBookModel) {
//        ebookViewModel.updateEbook(audioBookBase, audioBookBase.idAudioBook)
//        verifyUpdateEbook()
//    }

    private fun verifyUpdateEbook() {
        ebookViewModel.updateEbook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("Sucesso ao atualizar a capa")
                }
                is StateResource.Error -> {
                    toast("Erro ao atualizar a capa")
                }
                else -> {
                    toast("Erro inesperado ao atualizar a capa")
                }
            }
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
            toast("Sucesso ao subir")
            getURL(fileName)
        }.addOnFailureListener {
            toast("Falha ao subir")
        }
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