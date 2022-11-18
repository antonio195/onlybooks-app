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
import com.antoniocostadossantos.onlybooks.databinding.ActivityCreateEbookBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.gone
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.util.toast
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateEbookFragment(var ebookBase: EbookModel) : Fragment() {

    private lateinit var binding: ActivityCreateEbookBinding

    private val ebookViewModel: EbookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityCreateEbookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIsNewEbook()

        displayData(ebookBase)

        binding.sendBookImage.setOnClickListener {
            goToNewImage()
        }

        binding.publishEbook.setOnClickListener {
            checkFields()
        }
    }

    private fun displayData(ebook: EbookModel) {
        binding.inputTitle.setText(this.ebookBase.nameEbook)
        binding.inputCoAuthor.setText(ebook.coAuthorEbook)
        binding.inputGenre1.setText(this.ebookBase.genreEbook)
        binding.inputGenre2.setText(this.ebookBase.genre1Ebook)
        binding.inputGenre3.setText(this.ebookBase.genre2Ebook)
        binding.inputSinopse.setText(this.ebookBase.descricao)

        when (ebookBase.classificacao) {
            "Livre" -> binding.classLivre.isChecked = true
            "10" -> binding.class10.isChecked = true
            "12" -> binding.class12.isChecked = true
            "14" -> binding.class14.isChecked = true
            "16" -> binding.class16.isChecked = true
            "18" -> binding.class18.isChecked = true
        }

        val image = ebook.url

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

        transaction.replace(R.id.nav_host_fragment, StorageImageEbookFragment(ebookBase))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun checkFields() {
        when {
            binding.inputTitle.text.toString().isEmpty() -> {
                binding.inputTitle.error = "Preencha esse campo"
            }
            binding.inputGenre1.text.toString().isEmpty() -> {
                binding.inputGenre1.error = "Preencha esse campo"
            }
            binding.classRadioGroup.checkedRadioButtonId == -1 -> {
                toast("Selecione uma classificação indicativa")
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
        ebookBase.nameEbook = binding.inputTitle.text.toString()
        ebookBase.genreEbook = binding.inputGenre1.text.toString()
        ebookBase.genre1Ebook = binding.inputGenre2.text.toString()
        ebookBase.genre2Ebook = binding.inputGenre3.text.toString()
        ebookBase.descricao = binding.inputSinopse.text.toString()

        when (ebookBase.classificacao) {
            "Livre" -> binding.classLivre.isChecked = true
            "10" -> binding.class10.isChecked = true
            "12" -> binding.class12.isChecked = true
            "14" -> binding.class14.isChecked = true
            "16" -> binding.class16.isChecked = true
            "18" -> binding.class18.isChecked = true
        }
        ebookViewModel.updateEbook(ebookBase, ebookBase.idEbook)
        verifyUpdate()
    }

    private fun verifyUpdate() {
        ebookViewModel.updateEbook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    Toast.makeText(
                        (context as FragmentActivity),
                        "Ebook atualizado",
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
                        "Erro ao atualizar ebook",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    println("ERRO AO TENTAR ATUALIZAR EBOOK")
                    println(response)
                }
            }
        }
    }

    private fun postOrUpdate() {
        val isNewEbook = arguments?.getString("newEbook")
        if (!isNewEbook.isNullOrEmpty()) {

            ebookBase.nameEbook = binding.inputTitle.text.toString()
            ebookBase.authorEbook = getDataInCache("name")!!
            ebookBase.coAuthorEbook = binding.inputCoAuthor.text.toString()
            ebookBase.genreEbook = binding.inputGenre1.text.toString()
            ebookBase.genre1Ebook = binding.inputGenre2.text.toString()
            ebookBase.genre2Ebook = binding.inputGenre3.text.toString()
            ebookBase.descricao = binding.inputSinopse.text.toString()

            val radioButtonSelected = binding.classRadioGroup.checkedRadioButtonId

            when (radioButtonSelected) {
                binding.classLivre.id -> ebookBase.classificacao = "Livre"
                binding.class10.id -> ebookBase.classificacao = "10"
                binding.class12.id -> ebookBase.classificacao = "12"
                binding.class14.id -> ebookBase.classificacao = "14"
                binding.class16.id -> ebookBase.classificacao = "16"
                binding.class18.id -> ebookBase.classificacao = "18"
            }

            postEbook(ebookBase)
            goToNewBookFragment()
        } else {

            val radioButtonSelected = binding.classRadioGroup.checkedRadioButtonId

            when (radioButtonSelected) {
                binding.classLivre.id -> ebookBase.classificacao = "Livre"
                binding.class10.id -> ebookBase.classificacao = "10"
                binding.class12.id -> ebookBase.classificacao = "12"
                binding.class14.id -> ebookBase.classificacao = "14"
                binding.class16.id -> ebookBase.classificacao = "16"
                binding.class18.id -> ebookBase.classificacao = "18"
            }

            updateEbook()
        }
    }

    private fun goToNewBookFragment() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, NewBookFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun postEbook(ebookBase: EbookModel) {
        val idUser = getDataInCache("id")!!.toInt()
        ebookViewModel.postEbook(ebookBase, idUser)
        verifyPostEbook()
    }

    private fun verifyPostEbook() {
        ebookViewModel.postEbook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    toast("Ebook enviado")
                    println()
                    println()
                    println()
                    println()
                    println()
                    println()
                    println(ebookBase)
                    println(response.message)
                }
                is StateResource.Error -> {
                    toast("Erro ao enviar Ebook")
                }
                else -> {
                    toast("Erro inesperado ao enviar Ebook")
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