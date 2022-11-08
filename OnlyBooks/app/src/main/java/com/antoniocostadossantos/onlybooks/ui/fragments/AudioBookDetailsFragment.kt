package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentAudiobookDetailsBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioBookDetailsFragment(val audioBook: AudioBookModel) : Fragment() {

    private lateinit var binding: FragmentAudiobookDetailsBinding
    private val chapterViewModel: ChapterViewModel by viewModel()

    private var URLAudioBook: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAudiobookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayData()
        checkUrlExists()
        checkProperty()

        binding.editEbook.setOnClickListener {
            editAudioBook(audioBook)
        }

        binding.newChapter.setOnClickListener {
            goToNewChapter()
        }

        binding.ouvirAudiobook.setOnClickListener {
            listenEbook(URLAudioBook)
        }
    }

    private fun goToNewChapter() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, StorageFileAudioBookFragment(audioBook))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun listenEbook(URLAudioBook: String) {
        if (URLAudioBook.isEmpty()) {
            toast("AudioBook não possui capitulo")
        } else {
            val transaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()

            transaction.replace(
                R.id.nav_host_fragment,
                ListenAudioBookFragment(URLAudioBook)
            )
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun checkProperty() {
        if (audioBook.idUsuario.id == getDataInCache("id")?.toInt()) {
            binding.editEbook.show()
            binding.newChapter.show()
        }
    }

    private fun displayData() {
        binding.titleEbook.text = audioBook.nameAudioBook
        binding.genreEbook.text = audioBook.genreAudioBook
        binding.ebookSynopsis.text = audioBook.descricao
        val image = binding.imageEbook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.baixando_capa)
            .error(R.drawable.erro_capa)

        Glide.with(binding.imageEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(audioBook.urlAudioBook)
            .into(image)
    }

    private fun editAudioBook(audioBook: AudioBookModel) {
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, CreateAudioBookFragment(audioBook))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun checkUrlExists() {
        getUrl()
    }

    private fun getUrl() {
        chapterViewModel.getChapterAudioBook(audioBook.idAudioBook)
        checkUrlResponse()
    }

    private fun checkUrlResponse() {
        chapterViewModel.getChapterAudioBook.observe(viewLifecycleOwner) { response ->
            when (response) {

                is StateResource.Success -> {
                    println(response.data?.get(0)!!.urlAudio)
                    URLAudioBook = response.data?.get(0)!!.urlAudio
                }
                is StateResource.Error -> {
                }
                else -> {
                    println("AudioBookDetailsFragment linha 131")
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