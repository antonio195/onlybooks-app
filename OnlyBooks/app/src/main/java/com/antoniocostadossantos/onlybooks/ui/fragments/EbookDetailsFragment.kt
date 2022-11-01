package com.antoniocostadossantos.onlybooks.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentEbookDetailsBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.ui.ReadPDFURLActivity
import com.antoniocostadossantos.onlybooks.ui.StorageFileFragment
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class EbookDetailsFragment(val ebook: EbookModel) : Fragment() {

    private lateinit var binding: FragmentEbookDetailsBinding
    private val chapterViewModel: ChapterViewModel by viewModel()

    private var URLEbook: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEbookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ebook.idUsuario.id == getDataInCache("id")?.toInt()) {
            binding.editEbook.show()
            binding.newChapter.show()
        }

        checkUrlExists()

        binding.editEbook.setOnClickListener {
            editEbook(ebook)
            println(ebook)
        }

        binding.newChapter.setOnClickListener {
            val transaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment, StorageFileFragment(ebook))
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.titleEbook.text = ebook.nameEbook
        binding.genreEbook.text = ebook.genreEbook
        binding.ebookSynopsis.text = ebook.descricao
        val image = binding.imageEbook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(binding.imageEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebook.url)
            .into(image)

        binding.lerEbook.setOnClickListener {

            if (URLEbook.isNullOrEmpty()) {
                binding.lerEbook.isEnabled = false
                toast("Ebook não possui capitulo")
            } else {
                val intent = Intent((context as FragmentActivity), ReadPDFURLActivity::class.java)
                intent.putExtra(
                    "URLEbook",
                    URLEbook
                )
                startActivity(intent)
            }


        }
    }

    private fun editEbook(ebook: EbookModel) {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, CreateEbookFragment(ebook))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun checkUrlExists() {
        getUrl()
    }

    private fun getUrl() {
        chapterViewModel.getChapter(ebook.idEbook)
        checkUrlResponse()
    }

    private fun checkUrlResponse() {
        chapterViewModel.getChapter.observe(viewLifecycleOwner) { response ->
            when (response) {

                is StateResource.Success -> {
                    URLEbook = response.data?.get(0)!!.urlPDF
                }
                is StateResource.Error -> {
                }
                else -> {
                    println("EbookDetailsFragment linha 131")
                }
            }

        }
    }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }
}