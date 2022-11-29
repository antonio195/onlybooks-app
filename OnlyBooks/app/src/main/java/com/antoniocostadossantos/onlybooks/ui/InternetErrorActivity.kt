package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityInternetErrorBinding

class InternetErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInternetErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternetErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refresh.setOnClickListener {
            startActivity(Intent(this@InternetErrorActivity, SplashActivity::class.java))
            finish()
        }
    }
}