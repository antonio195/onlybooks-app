package com.antoniocostadossantos.onlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()
    }

    private fun displayData() {
        binding.textName.text = getDataInCache("name")
        binding.textDescription.text = getDataInCache("description")

        val photoURL = getDataInCache("photo")
        val photo = binding.profileImage

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.person_vector)
            .error(R.drawable.ic_baseline_error_24)

        Glide.with(binding.profileImage)
            .applyDefaultRequestOptions(requestOptions)
            .load(photoURL)
            .into(photo)
    }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }

}