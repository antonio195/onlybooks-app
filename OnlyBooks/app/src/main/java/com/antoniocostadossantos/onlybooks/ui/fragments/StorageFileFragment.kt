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
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageFileBinding
import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class StorageFileFragment(val ebookBase: EbookModel) : Fragment() {
    lateinit var binding: ActivityStorageFileBinding
    lateinit var fileUri: Uri
    private val chapterViewModel: ChapterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityStorageFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()

        binding.selectFile.setOnClickListener {
            selectFile()
        }

        binding.uploadFile.setOnClickListener {
            uploadImage()
        }
    }


    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("documents/$name").downloadUrl.addOnSuccessListener {
                postChapter(it.toString())
            }.addOnFailureListener {
                toast("Erro ao pegar URL")
            }
    }

    private fun postChapter(urlChapter: String) {
        val chapter = ChapterEbookMobile(ebookBase.idEbook, ebookBase.idUsuario.id, urlChapter)
        chapterViewModel.postChapter(chapter)
        verifyUpdate()
    }

    private fun verifyUpdate() {
        chapterViewModel.chapterResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("Sucesso ao enviar capítulo")
                }
                is StateResource.Error -> {
                    toast("Erro ao enviar capítulo")
                }
                else -> {
                    toast("Erro inesperado ao enviar capítulo.")
                }
            }

        }
    }


    private fun displayData() {
        binding.titleEbook.text = ebookBase.nameEbook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(binding.ivBannerEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebookBase.url)
            .into(binding.ivBannerEbook)
    }

    fun selectFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    fun uploadImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("documents/$fileName")

        storageReference.putFile(fileUri).addOnSuccessListener {
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

                fileUri = data?.data!!
            }
        }
}