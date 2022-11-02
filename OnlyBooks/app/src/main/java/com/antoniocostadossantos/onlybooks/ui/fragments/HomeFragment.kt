package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.antoniocostadossantos.onlybooks.databinding.FragmentHomeBinding
import com.antoniocostadossantos.onlybooks.ui.adapter.ItemAdapter
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var ebookAdapter: ItemAdapter
    private val ebookViewModel: EbookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        this.ebookAdapter = ItemAdapter(binding.root.context)

        val recyclerView = binding.sugestoesRecyclerview

        recyclerView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recyclerView.adapter = this.ebookAdapter

        ebookViewModel.suggestions.observe(viewLifecycleOwner) { response ->
            this.ebookAdapter.setList(response.data!!)
        }
    }
}