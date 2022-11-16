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
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.antoniocostadossantos.onlybooks.viewModel.LibraryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioBookDetailsFragment(val audioBook: AudioBookModel) : Fragment() {

    private lateinit var binding: FragmentAudiobookDetailsBinding
    private val chapterViewModel: ChapterViewModel by viewModel()
    private val libraryViewModel: LibraryViewModel by viewModel()
    private val audioBookViewModel: AudioBookViewModel by viewModel()

    var audioBookExistsInLibrary: Boolean = false

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
        existsAudioBookInLibrary()
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
            if (audioBookExistsInLibrary) {
                deleteAudioBookInLibrary()
            } else {
                addAudioBookInLibrary()
            }
        }

        binding.deleteAudiobook.setOnClickListener {
            deleteAudioBook()
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
            binding.deleteAudiobook.show()
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

    private fun existsAudioBookInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.existsAubioBookInLibrary(idUser, audioBook.idAudioBook)
        verifyExistsAudioBookInLibrary()
    }

    private fun verifyExistsAudioBookInLibrary() {
        libraryViewModel.existsAubioBookInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    if (response.data == true) {
                        audioBookExistsInLibrary = true
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

    private fun deleteAudioBookInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.deleteAubioBookInLibrary(idUser, audioBook.idAudioBook)
        verifyDeleteAudioBookInLibrary()
    }

    private fun verifyDeleteAudioBookInLibrary() {
        libraryViewModel.deleteAubioBookInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    if (response.data == true) {
                        audioBookExistsInLibrary = false
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

    private fun addAudioBookInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.addAudioBookInLibrary(idUser, audioBook.idAudioBook)
        verifyAddAudioBookInLibrary()
    }

    private fun verifyAddAudioBookInLibrary() {
        libraryViewModel.addAudioBookInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    audioBookExistsInLibrary = true
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

    private fun deleteAudioBook() {
        val idAudioBook = audioBook.idAudioBook
        audioBookViewModel.deleteAudioBook(idAudioBook)
        verifyDeleteAudioBook()
    }

    private fun verifyDeleteAudioBook() {
        audioBookViewModel.deleteAudioBook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("AudioBook deletado")
                    goToAudioBookFragment()
                }
                is StateResource.Error -> {
                    toast("Erro ao deletar")
                }
                else -> {
                }
            }
        }
    }

    private fun goToAudioBookFragment() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, AudioBookFragment())
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