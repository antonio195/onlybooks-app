package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentAudioBookBinding

class AudioBookFragment : Fragment(R.layout.fragment_audio_book) {

    private lateinit var binding: FragmentAudioBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioBookBinding.inflate(inflater, container, false)
        return binding.root
    }

}