package com.antoniocostadossantos.onlybooks.ui

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.onlybooks.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLer.setOnClickListener {
            val intent = Intent(this@HomeActivity, WebViewActivity::class.java)

            val url = "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/documents%2F2022_09_15_09_48_36?alt=media&token=3a89e859-3a63-4891-a819-7b65e1e24219"

            intent.putExtra("pdf_url", url)

            startActivity(intent)
        }
    }
}