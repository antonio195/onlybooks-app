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
import com.antoniocostadossantos.onlybooks.viewModel.LibraryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioBookDetailsFragment(val audioBook: AudioBookModel) : Fragment() {

    private lateinit var binding: FragmentAudiobookDetailsBinding
    private val chapterViewModel: ChapterViewModel by viewModel()
    private val libraryViewModel: LibraryViewModel by viewModel()

    var ebookExistsInLibrary: Boolean = false

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
        existsInLibrary()
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

        binding.saveAudiobook.setOnClickListener {
            if (ebookExistsInLibrary) {
                deleteInLibrary()
            } else {
                addInLibrary()
            }
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

    private fun existsInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.existsInLibrary(idUser, audioBook.idAudioBook)
        verifyExistsInLibrary()
    }

    private fun verifyExistsInLibrary() {
        libraryViewModel.existsInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    if (response.data == true) {
                        ebookExistsInLibrary = true
                        binding.saveAudiobook.setImageResource(R.drawable.ic_favorited)
                    }
                }
                is StateResource.Error -> {
                }
                else -> {
                    println("AudioBookDetailsFragment linha 131")
                }
            }
        }
    }

    private fun deleteInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.deleteInLibrary(idUser, audioBook.idAudioBook)
        verifyDeleteInLibrary()
    }

    private fun verifyDeleteInLibrary() {
        libraryViewModel.deleteInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    if (response.data == true) {
                        ebookExistsInLibrary = false
                        binding.saveAudiobook.setImageResource(R.drawable.ic_not_favorited)
                        toast("AudioBook removido da biblioteca")
                    }
                }
                is StateResource.Error -> {
                }
                else -> {
                    println("AudioBookDetailsFragment linha 131")
                }
            }
        }
    }

    private fun addInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.addInLibrary(idUser, audioBook.idAudioBook)
        verifyAddInLibrary()
    }

    private fun verifyAddInLibrary() {
        libraryViewModel.addInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    ebookExistsInLibrary = true
                    binding.saveAudiobook.setImageResource(R.drawable.ic_favorited)
                    toast("AudioBook adicionado da biblioteca")
                }
                is StateResource.Error -> {
                }
                else -> {
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