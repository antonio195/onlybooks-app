package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentHomeBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.ui.adapter.AudioBookItemHorizontalAdapter
import com.antoniocostadossantos.onlybooks.ui.adapter.EbookItemHorizontalAdapter
import com.antoniocostadossantos.onlybooks.util.StateResource
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var ebookAdapter: EbookItemHorizontalAdapter
    private lateinit var audiobookAdapter: AudioBookItemHorizontalAdapter
    private val ebookViewModel: EbookViewModel by viewModel()
    private val audioBookViewModel: AudioBookViewModel by viewModel()

    private lateinit var ebookSpotlight: EbookModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAudioBookRecyclerView()
        setupEbookRecyclerView()

        getSpotlightWeek()

        binding.btnConhecer.setOnClickListener {
            goToEbookDetails()
        }

        binding.iconSearch.setOnClickListener {
            goToSearch()
        }
    }

    private fun setupEbookRecyclerView() {
        this.ebookAdapter = EbookItemHorizontalAdapter(binding.root.context)

        val recyclerView = binding.sugestoesRecyclerviewEbook

        recyclerView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recyclerView.adapter = this.ebookAdapter

        ebookViewModel.suggestions.observe(viewLifecycleOwner) { response ->
            val list = response.data!!.toMutableList()
            val newList = mutableListOf<EbookModel>()
            list.shuffle()
            newList.add(list[1])
            newList.add(list[2])
            newList.add(list[3])
            newList.toList()
            this.ebookAdapter.setList(newList)
        }
    }

    private fun setupAudioBookRecyclerView() {
        this.audiobookAdapter = AudioBookItemHorizontalAdapter(binding.root.context)

        val recyclerView = binding.sugestoesRecyclerviewAudiobook

        recyclerView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recyclerView.adapter = this.audiobookAdapter

        audioBookViewModel.suggestions.observe(viewLifecycleOwner) { response ->
            val list = response.data!!.toMutableList()
            val newList = mutableListOf<AudioBookModel>()
            list.shuffle()
            newList.add(list[1])
            newList.add(list[2])
            newList.add(list[3])
            newList.toList()
            this.audiobookAdapter.setList(newList)
        }
    }

    private fun getSpotlightWeek() {
        ebookViewModel.getSpotlightWeek()
        verifySpotlightWeek()
    }

    private fun verifySpotlightWeek() {
        ebookViewModel.spotlightWeek.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    displayData(response.data)
                    ebookSpotlight = response.data!!
                }
                is StateResource.Error -> {
                }
                else -> {
                    println(response)
                }
            }
        }
    }

    private fun goToEbookDetails() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(
            R.id.nav_host_fragment,
            EbookDetailsFragment(ebookSpotlight)
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun goToSearch() {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(
            R.id.nav_host_fragment,
            SearchFragment()
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun displayData(ebookModel: EbookModel?) {
        val image = binding.imageDestaque

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.baixando_capa)
            .error(R.drawable.erro_capa)

        Glide.with(image)
            .applyDefaultRequestOptions(requestOptions)
            .load(ebookModel!!.url)
            .into(image)
    }
}