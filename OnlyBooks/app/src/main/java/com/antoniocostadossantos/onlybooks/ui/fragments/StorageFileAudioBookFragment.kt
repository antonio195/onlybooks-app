package com.antoniocostadossantos.onlybooks.ui.fragments

import android.annotation.SuppressLint
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
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageAudio2Binding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.model.ChapterAudioBookMobile
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.gone
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class StorageFileAudioBookFragment(val audioBookBase: AudioBookModel) : Fragment() {
    lateinit var binding: ActivityStorageAudio2Binding
    lateinit var fileUri: Uri
    private val chapterViewModel: ChapterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityStorageAudio2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()

        binding.selectFile.setOnClickListener {
            selectFile()
        }

        binding.uploadFile.setOnClickListener {
            if (::fileUri.isInitialized) {
                uploadImage()
                progressBar("bla")
            } else {
                toast("Selecione um arquivo primeiro")
            }

        }
    }


    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("audios/$name").downloadUrl.addOnSuccessListener {
                postChapter(it.toString())
            }.addOnFailureListener {
                toast("Erro ao pegar URL")
            }
    }

    private fun postChapter(urlChapter: String) {
        val chapter =
            ChapterAudioBookMobile(
                audioBookBase.idAudioBook,
                audioBookBase.idUsuario.id,
                urlChapter
            )
        chapterViewModel.postChapterAudioBook(chapter)
        verifyUpdate()
    }

    private fun verifyUpdate() {
        chapterViewModel.chapterAudioBookResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    progressBar("sucess")
                }
                is StateResource.Error -> {
                    progressBar("error")
                }
                else -> {
                    toast("Erro inesperado ao enviar capítulo.")
                }
            }

        }
    }

    private fun displayData() {
        binding.titleEbook.text = audioBookBase.nameAudioBook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.baixando_capa)
            .error(R.drawable.erro_capa)

        Glide.with(binding.ivBannerEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(audioBookBase.urlAudioBook)
            .into(binding.ivBannerEbook)
    }

    fun selectFile() {
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    fun uploadImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("audios/$fileName")

        storageReference.putFile(fileUri).addOnSuccessListener {
            getURL(fileName)
            progressBar("sucess")
        }.addOnFailureListener {
            progressBar("error")
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                fileUri = data?.data!!
            }
        }

    @SuppressLint("ResourceAsColor")
    private fun progressBar(status: String) {
        when (status) {
            "error" -> {
                binding.textProguess.show()
                binding.textProguess.setTextColor(R.color.red)
                binding.textProguess.text = "Erro ao enviar o áudio"
                binding.progressBar.gone()
            }
            "sucess" -> {
                binding.textProguess.show()
                binding.textProguess.setTextColor(R.color.green)
                binding.textProguess.text = "Sucesso ao enviar o áudio"
                binding.progressBar.gone()
            }
            else -> {
                binding.textProguess.show()
                binding.progressBar.show()
            }
        }
    }
}