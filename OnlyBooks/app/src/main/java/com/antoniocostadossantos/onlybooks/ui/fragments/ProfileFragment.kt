package com.antoniocostadossantos.onlybooks.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.FragmentProfileBinding
import com.antoniocostadossantos.onlybooks.util.SecurityPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.textName.text = getDataInCache("name")
        binding.textDescription.text = getDataInCache("description")

        val photoURL = getDataInCache("photo")
        val photo = binding.profileImage

        println(getDataInCache("name"))
        println(getDataInCache("description"))
        println(getDataInCache("photo"))

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_error_24)
            .error(R.drawable.ic_baseline_cloud_download_24)

        Glide.with(binding.profileImage)
            .applyDefaultRequestOptions(requestOptions)
            .load(photoURL)
            .into(photo)

        return binding.root
    }

    private fun saveInCache(key: String, value: String) {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = preferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    private fun getDataInCache(key: String): String? {
        val preferences = activity?.getSharedPreferences(
            "UserData",
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences?.getString(key, "")
    }

}