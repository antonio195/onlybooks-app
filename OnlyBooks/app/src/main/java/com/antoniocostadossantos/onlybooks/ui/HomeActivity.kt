package com.antoniocostadossantos.onlybooks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antoniocostadossantos.onlybooks.databinding.ActivityForgotPasswordBinding
import com.antoniocostadossantos.onlybooks.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}