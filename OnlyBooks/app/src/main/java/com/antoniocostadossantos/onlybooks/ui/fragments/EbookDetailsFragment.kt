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
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import com.antoniocostadossantos.onlybooks.viewModel.LibraryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class EbookDetailsFragment(val ebook: EbookModel) : Fragment() {

    private lateinit var binding: FragmentEbookDetailsBinding
    private val chapterViewModel: ChapterViewModel by viewModel()
    private val libraryViewModel: LibraryViewModel by viewModel()
    private val ebookViewModel: EbookViewModel by viewModel()
    var ebookExistsInLibrary: Boolean = false


    private var URLEbook: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEbookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayData()
        existsEbookInLibrary()
        checkUrlExists()
        checkProperty()

        binding.editEbook.setOnClickListener {
            editEbook(ebook)
        }

        binding.newChapter.setOnClickListener {
            goToNewChapter()
        }

        binding.lerEbook.setOnClickListener {
            readEbook()
        }

        binding.saveEbook.setOnClickListener {
            if (ebookExistsInLibrary) {
                deleteEbookInLibrary()
            } else {
                addEbookInLibrary()
            }
        }

        binding.deleteEbook.setOnClickListener {
            deleteEbook()
        }
    }

    private fun goToNewChapter() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, StorageFileEbookFragment(ebook))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun readEbook() {
        if (URLEbook.isEmpty()) {
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

    private fun checkProperty() {
        if (ebook.idUsuario.id == getDataInCache("id")?.toInt()) {
            binding.editEbook.show()
            binding.newChapter.show()
            binding.deleteEbook.show()
        }
    }

    private fun displayData() {
        binding.titleEbook.text = ebook.nameEbook
        binding.genreEbook.text = ebook.genreEbook
        binding.ebookSynopsis.text = ebook.descricao
        val image = binding.imageEbook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.baixando_capa)
            .error(R.drawable.erro_capa)

        Glide.with(binding.imageEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebook.url)
            .into(image)
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
        chapterViewModel.getChapterEbook(ebook.idEbook)
        checkUrlResponse()
    }

    private fun checkUrlResponse() {
        chapterViewModel.getChapterEbook.observe(viewLifecycleOwner) { response ->
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


    private fun existsEbookInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.existsEbookInLibrary(idUser, ebook.idEbook)
        verifyExistsEbookInLibrary()
    }

    private fun verifyExistsEbookInLibrary() {
        libraryViewModel.existsEbookInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    if (response.data == true) {
                        ebookExistsInLibrary = true
                        binding.saveEbook.setImageResource(R.drawable.ic_favorited)
                    }
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }
    }

    private fun deleteEbookInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.deleteEbookInLibrary(idUser, ebook.idEbook)
        verifyDeleteEbookInLibrary()
    }

    private fun verifyDeleteEbookInLibrary() {
        libraryViewModel.deleteEbookInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    if (response.data == true) {
                        ebookExistsInLibrary = false
                        binding.saveEbook.setImageResource(R.drawable.ic_not_favorited)
                        toast("Ebook removido da biblioteca")
                    }
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }
    }

    private fun addEbookInLibrary() {
        val idUser = getDataInCache("id")!!.toInt()
        libraryViewModel.addEbookInLibrary(idUser, ebook.idEbook)
        verifyAddEbookInLibrary()
    }

    private fun verifyAddEbookInLibrary() {
        libraryViewModel.addEbookInLibrary.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    ebookExistsInLibrary = true
                    binding.saveEbook.setImageResource(R.drawable.ic_favorited)
                    toast("Ebook adicionado da biblioteca")
                }
                is StateResource.Error -> {
                }
                else -> {
                }
            }
        }
    }

    private fun deleteEbook() {
        val idEbook = ebook.idEbook
        ebookViewModel.deleteEbook(idEbook)
        verifyDeleteEbook()
    }

    private fun verifyDeleteEbook() {
        ebookViewModel.deleteEbook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("Ebook deletado")
                    goToEbookFragment()
                }
                is StateResource.Error -> {
                    toast("Erro ao deletar")
                }
                else -> {
                }
            }
        }
    }

    private fun goToEbookFragment() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, EbookFragment())
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