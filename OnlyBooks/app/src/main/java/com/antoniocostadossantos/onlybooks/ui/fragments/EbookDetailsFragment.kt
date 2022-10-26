package com.antoniocostadossantos.onlybooks.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentEbookDetailsBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
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

        binding.titleEbook.text = ebook.nameEbook
        binding.genreEbook.text = ebook.genreEbook
        binding.ebookSynopsis.text = ebook.descricao
        val image = binding.imageEbook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.person_vector)
            .error(R.drawable.person_vector)

        Glide.with(binding.imageEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebook.url)
            .into(image)

        binding.lerEbook.setOnClickListener {
            val intent = Intent((context as FragmentActivity), WebViewActivity::class.java)
            startActivity(intent)
        }

    }
}