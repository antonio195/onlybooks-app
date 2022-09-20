package com.antoniocostadossantos.onlybooks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ActivityHomeBinding
import com.antoniocostadossantos.onlybooks.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl("https://onlybooksstoragev2.blob.core.windows.net/armazenamentov2/armazenamentov2/Harry-Potter-e-a-pedra-filosofal.pdf")
        }

    }
}