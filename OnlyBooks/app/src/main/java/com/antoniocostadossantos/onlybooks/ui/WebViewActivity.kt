package com.antoniocostadossantos.onlybooks.ui

import android.content.res.AssetManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.antoniocostadossantos.onlybooks.databinding.ActivityWebViewBinding
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File


class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val uri = intent.getStringExtra("pdf_url")?.toUri()

        binding.pdfView.fromAsset("Harry-Potter-e-a-pedra-filosofal.pdf")
            .enableSwipe(true)
            .swipeHorizontal(true)
            .defaultPage(0)
            .pageSnap(true)
            .autoSpacing(true)
            .enableAntialiasing(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .pageFling(true)
            .nightMode(false)
            .load()
    }


    fun getDownloadedFile() {
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        println("------------------------------------------------------------------------------------")
        val path = Environment.getExternalStorageDirectory().toString() + "/assets/"

        println("Path $path")
        val directory = File(path)
        val files = directory.listFiles()
        if (directory.canRead() && files != null) {
            println("Files size: ${files.size}")
            for (file in files) Log.d("FILE", file.name)
        } else Log.d("Null?", "it is null")
    }
}