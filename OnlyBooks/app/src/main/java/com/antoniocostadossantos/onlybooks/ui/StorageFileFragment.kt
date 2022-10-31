package com.antoniocostadossantos.onlybooks.ui

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
import com.antoniocostadossantos.onlybooks.databinding.ActivityStorageFileBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.model.ChapterModel
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
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
    ): View? {
        binding = ActivityStorageFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()

        binding.btnSelectedFile.setOnClickListener {
            selectFile()
        }

        binding.btnUploadFile.setOnClickListener {
            uploadFile()
        }
    }

    private fun setupData() {

        binding.titleEbook.text = ebookBase.nameEbook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(binding.ivBannerEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebookBase.url)
            .into(binding.ivBannerEbook)

    }

    private fun postChapter(nameChapter: String, urlChapter: String) {
        val audioBookEmpyt = AudioBookModel(
            "EbookPostado",
            "18",
            "",
            "EbookPostado",
            true,
            "",
            "",
            "Socorro",
            0,
            ebookBase.idUsuario,
            "Socorro jesus",
            "",
            "Jesus Cristo",
            0,
            true,
            ""
        )
        val chapter = ChapterModel(audioBookEmpyt, 1, ebookBase, nameChapter, 1, urlChapter)
        chapterViewModel.postChapter(chapter, ebookBase.idEbook, true)
    }

    private fun verifyUpdate() {
        chapterViewModel.chapterResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    Toast.makeText(
                        (context as FragmentActivity),
                        "ENVIO CAPOTULO",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is StateResource.Loading -> {
                    Toast.makeText(
                        (context as FragmentActivity),
                        "ENVIO CAPOTULO",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is StateResource.Error -> {
                    Toast.makeText((context as FragmentActivity), "ERRO CAPITUO", Toast.LENGTH_LONG)
                        .show()
                }

                else -> {
                    Toast.makeText(
                        (context as FragmentActivity),
                        "ERRO CAPITULO = ELSE",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    fun getURL(name: String) {
        FirebaseStorage.getInstance()
            .getReference("documents/$name").downloadUrl.addOnSuccessListener { url ->
                val nameChapter = binding.nameChapter.text.toString()
                postChapter(nameChapter, url.toString())
                verifyUpdate()
            }.addOnFailureListener {
                Toast.makeText((context as FragmentActivity), it.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
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
            Toast.makeText((context as FragmentActivity), "Sucesso ao subir", Toast.LENGTH_SHORT)
                .show()
            getURL(fileName)
        }.addOnFailureListener {
            Toast.makeText((context as FragmentActivity), "Falha ao subir", Toast.LENGTH_SHORT)
                .show()
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