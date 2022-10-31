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
import com.antoniocostadossantos.onlybooks.ui.StorageFileFragment
import com.antoniocostadossantos.onlybooks.ui.WebViewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class EbookDetailsFragment(val ebook: EbookModel) : Fragment() {

    private lateinit var binding: FragmentEbookDetailsBinding

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

        checkChapterExist()

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
            val intent = Intent((context as FragmentActivity), WebViewActivity::class.java)
            intent.putExtra(
                "URLChapter",
                "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/documents%2Fchapeuzinho_vermelho_versao_digital.pdf?alt=media&token=ee7b6125-2906-4531-8fda-028c3232db75\n"
            )
            startActivity(intent)
        }
    }

    private fun getChapter() {

    }

    private fun checkChapterExist() {

    }

    private fun editEbook(ebook: EbookModel) {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, CreateEbookFragment(ebook))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }
}