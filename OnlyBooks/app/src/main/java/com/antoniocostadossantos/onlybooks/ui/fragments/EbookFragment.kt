package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.antoniocostadossantos.onlybooks.databinding.FragmentEbookBinding
import com.antoniocostadossantos.onlybooks.ui.adapter.EbookItemVerticalAdapter
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EbookFragment : Fragment() {

    private lateinit var binding: FragmentEbookBinding
    private lateinit var ebookAdapter: EbookItemVerticalAdapter
    private val ebookViewModel: EbookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEbookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        this.ebookAdapter = EbookItemVerticalAdapter(binding.root.context)

        val recyclerView = binding.sugestoesRecyclerview

        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        recyclerView.adapter = this.ebookAdapter

        ebookViewModel.suggestions.observe(viewLifecycleOwner) { response ->
            this.ebookAdapter.setList(response.data!!)
        }
    }
}