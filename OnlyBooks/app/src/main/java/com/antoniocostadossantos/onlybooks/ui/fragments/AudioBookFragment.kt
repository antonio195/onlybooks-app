package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentAudioBookBinding
import com.antoniocostadossantos.onlybooks.ui.adapter.AudiobookItemAdapter
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioBookFragment : Fragment(R.layout.fragment_audio_book) {

    private lateinit var binding: FragmentAudioBookBinding
    private lateinit var audioBookItemAdapter: AudiobookItemAdapter
    private val audioBookViewModel: AudioBookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        this.audioBookItemAdapter = AudiobookItemAdapter(binding.root.context)

        val recyclerView = binding.audiobookSugestoesRecyclerview

//        recyclerView.layoutManager = LinearLayoutManager(
//            activity?.applicationContext,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )

        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        recyclerView.adapter = this.audioBookItemAdapter

        audioBookViewModel.suggestions.observe(viewLifecycleOwner) { response ->
            this.audioBookItemAdapter.setList(response.data!!)
        }
    }

}