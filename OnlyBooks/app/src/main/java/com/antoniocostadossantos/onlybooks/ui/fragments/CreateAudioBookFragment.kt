package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ActivityCreateAudiobookBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.gone
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateAudioBookFragment(var audioBookBase: AudioBookModel) : Fragment() {

    private lateinit var binding: ActivityCreateAudiobookBinding

    private val audioBookViewModel: AudioBookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityCreateAudiobookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIsNewEbook()

        displayData(audioBookBase)

        binding.sendBookImage.setOnClickListener {
            goToNewImage()
        }

        binding.publishEbook.setOnClickListener {
            checkFields()
        }
    }

    private fun displayData(audioBookBase: AudioBookModel) {
        binding.inputTitle.setText(audioBookBase.nameAudioBook)
        binding.inputCoAuthor.setText(audioBookBase.coAuthorAudioBook)
        binding.inputGenre1.setText(audioBookBase.genreAudioBook)
        binding.inputGenre2.setText(audioBookBase.genre1AudioBook)
        binding.inputGenre3.setText(audioBookBase.genre2AudioBook)
        binding.inputClass.setText(audioBookBase.classificacao)
        binding.inputSinopse.setText(audioBookBase.descricao)
        binding.inputStoryteller1.setText(audioBookBase.narradorAudioBook)
        binding.inputStoryteller2.setText(audioBookBase.narrador2AudioBook)
        val image = audioBookBase.urlAudioBook

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.baixando_capa)
            .error(R.drawable.erro_capa)

        Glide.with(binding.imageEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(image)
            .into(binding.imageEbook)
    }

    private fun goToNewImage() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, StorageImageAudioBookFragment(audioBookBase))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun goToNewBookFragment() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, NewBookFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun checkFields() {
        when {
            binding.inputTitle.text.toString().isEmpty() -> {
                binding.inputTitle.error = "Preencha esse campo"
            }
            binding.inputStoryteller1.text.toString().isEmpty() -> {
                binding.inputStoryteller1.error = "Preencha esse campo"
            }
            binding.inputGenre1.text.toString().isEmpty() -> {
                binding.inputGenre1.error = "Preencha esse campo"
            }
            binding.inputClass.text.toString().isEmpty() -> {
                binding.inputClass.error = "Preencha esse campo"
            }
            binding.inputSinopse.text.toString().isEmpty() -> {
                binding.inputSinopse.error = "Preencha esse campo"
            }
            else -> {
                postOrUpdate()
            }
        }
    }

    private fun updateEbook() {
        audioBookBase.nameAudioBook = binding.inputTitle.text.toString()
        audioBookBase.genreAudioBook = binding.inputGenre1.text.toString()
        audioBookBase.genre1AudioBook = binding.inputGenre2.text.toString()
        audioBookBase.genre2AudioBook = binding.inputGenre3.text.toString()
        audioBookBase.classificacao = binding.inputClass.text.toString()
        audioBookBase.descricao = binding.inputSinopse.text.toString()

        audioBookViewModel.updateAudioBook(audioBookBase, audioBookBase.idAudioBook)
        verifyUpdate()
    }

    private fun verifyUpdate() {
        audioBookViewModel.updateAudioBook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    Toast.makeText(
                        (context as FragmentActivity),
                        "AudioBook atualizado",
                        Toast.LENGTH_SHORT
                    ).show()

                    val transaction =
                        (context as FragmentActivity).supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.nav_host_fragment, HomeFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
                is StateResource.Error -> {
                    Toast.makeText(
                        (context as FragmentActivity),
                        "Erro ao atualizar AudioBook",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    println("ERRO AO TENTAR ATUALIZAR AudioBook")
                    println(response)
                }
            }
        }
    }

    private fun postOrUpdate() {
        val isNewEbook = arguments?.getString("newEbook")
        if (!isNewEbook.isNullOrEmpty()) {

            audioBookBase.authorAudioBook = getDataInCache("name")!!
            audioBookBase.classificacao = binding.inputClass.text.toString()
            audioBookBase.coAuthorAudioBook = binding.inputCoAuthor.text.toString()
            audioBookBase.descricao = binding.inputSinopse.text.toString()
            audioBookBase.isEbook = true
            audioBookBase.genre1AudioBook = binding.inputGenre2.text.toString()
            audioBookBase.genre2AudioBook = binding.inputGenre3.text.toString()
            audioBookBase.genreAudioBook = binding.inputGenre1.text.toString()
            audioBookBase.idAudioBook = 0
            audioBookBase.nameAudioBook = binding.inputTitle.text.toString()
            audioBookBase.narrador2AudioBook = binding.inputStoryteller2.text.toString()
            audioBookBase.narradorAudioBook = binding.inputStoryteller1.text.toString()
            audioBookBase.statusAudioBook = true

            postAudioBook(audioBookBase)

            goToNewBookFragment()
        } else {
            updateEbook()
        }
    }

    private fun postAudioBook(audioBookBase: AudioBookModel) {
        val idUser = getDataInCache("id")!!.toInt()
        audioBookViewModel.postAudioBook(audioBookBase, idUser)
        verifyPost()
    }

    private fun verifyPost() {
        audioBookViewModel.postAudioBook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("AudioBook enviado")
                }
                is StateResource.Error -> {
                    toast("Erro ao enviar AudioBook")
                }
                else -> {
                    toast("Erro inesperado ao enviar AudioBook")
                }
            }
        }
    }

    private fun checkIsNewEbook() {
        val isNewEbook = arguments?.getString("newEbook")
        if (!isNewEbook.isNullOrEmpty()) {
            binding.imageEbook.gone()
            binding.sendBookImage.gone()
            binding.infoBoxCover.show()
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