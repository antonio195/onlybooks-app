package com.antoniocostadossantos.onlybooks.ui

import android.app.DownloadManager
import android.os.*
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.antoniocostadossantos.onlybooks.databinding.ActivityWebViewBinding
import com.antoniocostadossantos.onlybooks.util.DownloadReceiver
import com.antoniocostadossantos.onlybooks.util.hide
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File


class WebViewActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private val downloadReceiver: DownloadReceiver = DownloadReceiver()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startDownload(intent.getStringExtra("URLChapter").toString())
    }

    private fun startDownload(URLChapter: String) {

        val title = URLUtil.guessFileName(URLChapter, null, null)

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            title
        )

//        val file = File(
//            applicationContext.cacheDir,
//            title
//        )

        if (file.exists()) {
            binding.progressBar.hide()
            binding.textLoadEbook.hide()
            setupPDFViewer(file)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val request = DownloadManager.Request(URLChapter.toUri())
                val title = URLUtil.guessFileName(URLChapter, null, null)
                request.setTitle(title)
                request.setDescription("Baixando")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)

                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val enqueue = downloadManager.enqueue(request)
            }, 2000)

            Handler(Looper.getMainLooper()).postDelayed({
                binding.progressBar.hide()
                binding.textLoadEbook.hide()
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    title
                )
                setupPDFViewer(file)
            }, 10000)
        }

    }

    fun setupPDFViewer(file: File) {
        binding.pdfView.fromUri(file.toUri())
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
}