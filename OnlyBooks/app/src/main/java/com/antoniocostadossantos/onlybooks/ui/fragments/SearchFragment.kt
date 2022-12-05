package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.antoniocostadossantos.onlybooks.databinding.FragmentSearchBinding
import com.antoniocostadossantos.onlybooks.ui.adapter.AudiobookItemVerticalAdapter
import com.antoniocostadossantos.onlybooks.ui.adapter.EbookItemVerticalAdapter
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.util.hide
import com.antoniocostadossantos.onlybooks.util.show
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var ebookAdapter: EbookItemVerticalAdapter
    private val ebookViewModel: EbookViewModel by viewModel()

    private lateinit var audioBookItemAdapter: AudiobookItemVerticalAdapter
    private val audioBookViewModel: AudioBookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seeEbooks.setOnClickListener {
            binding.recyclerViewEbook.show()
            binding.recyclerViewAudiobook.hide()
        }

        binding.seeAudiobooks.setOnClickListener {
            binding.recyclerViewEbook.hide()
            binding.recyclerViewAudiobook.show()
        }

        binding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val pesquisa = binding.inputSearch.text.toString()
                if (pesquisa.isNotEmpty()) {
                    ebookViewModel.searchEbook(pesquisa)
                    audioBookViewModel.searchAudioBook(pesquisa)
                }
                binding.progressBar.show()
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.progressBar.hide()
                setupRecyclerViewAudioBook()
                setupRecyclerViewEbook()

            }

        })
    }

    private fun setupRecyclerViewAudioBook() {
        this.audioBookItemAdapter = AudiobookItemVerticalAdapter(binding.root.context)

        val recyclerView = binding.recyclerViewAudiobook

        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        recyclerView.adapter = this.audioBookItemAdapter


        audioBookViewModel.searchAudioBook.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    audioBookItemAdapter.setList(response.data!!)
                }
                is StateResource.Error -> {
                }
                is StateResource.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
    }

    private fun setupRecyclerViewEbook() {
        this.ebookAdapter = EbookItemVerticalAdapter(binding.root.context)

        val recyclerView = binding.recyclerViewEbook

        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        recyclerView.adapter = this.ebookAdapter

        ebookViewModel.searchEbook.observe(viewLifecycleOwner) { response ->

            when (response) {
                is StateResource.Success -> {
                    ebookAdapter.setList(response.data!!)
                }
                is StateResource.Error -> {
                }
                is StateResource.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
    }


}