package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ActivityCreateEbookBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.util.StateResource
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

        setData(ebookBase)

        binding.sendBookImage.setOnClickListener {
            val transaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment, StorageImageFragment(ebookBase))
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.publishEbook.setOnClickListener {
            checkFields()
        }
    }

    private fun checkFields() {
        when {
            binding.inputTitle.text.toString().isEmpty() -> {
                binding.inputTitle.error = "Preencha esse campo"
            }

            binding.inputGenre1.text.toString().isEmpty() -> {
                binding.inputGenre1.error = "Preencha esse campo"
            }

            binding.inputClass.text.toString().isEmpty() -> {
                binding.inputClass.error = "Preencha esse campo"
            }

            binding.inputSinopse.text.toString().isEmpty() -> {
                binding.inputGenre1.error = "Preencha esse campo"
            }

            else -> {
                updateEbook()
            }
        }
    }

    private fun updateEbook() {
        ebookBase.nameEbook = binding.inputTitle.text.toString()
        ebookBase.genreEbook = binding.inputGenre1.text.toString()
        ebookBase.genre1Ebook = binding.inputGenre2.text.toString()
        ebookBase.genre2Ebook = binding.inputGenre3.text.toString()
        ebookBase.classificacao = binding.inputClass.text.toString()
        ebookBase.descricao = binding.inputSinopse.text.toString()

        println(ebookBase)
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

    private fun setData(ebook: EbookModel) {
        binding.inputTitle.setText(this.ebookBase.nameEbook)
        binding.inputGenre1.setText(this.ebookBase.genreEbook)
        binding.inputGenre2.setText(this.ebookBase.genre1Ebook)
        binding.inputGenre3.setText(this.ebookBase.genre2Ebook)
        binding.inputClass.setText(this.ebookBase.classificacao)
        binding.inputSinopse.setText(this.ebookBase.descricao)
        val image = ebook.url

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(binding.imageEbook)
            .applyDefaultRequestOptions(requestOptions)
            .load(image)
            .into(binding.imageEbook)
    }
}