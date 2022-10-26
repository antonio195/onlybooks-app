package com.antoniocostadossantos.onlybooks.ui

import android.app.DownloadManager
import android.content.Context
import android.os.*
import android.os.storage.StorageManager
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
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


//        val uri = intent.getStringExtra("pdf_url")?.toUri()

        val fileStorage = StorageFileActivity()

//        val path = fileStorage.downloadFromName("Harry-Potter-e-a-pedra-filosofal.pdf")


        val path =
            "https://firebasestorage.googleapis.com/v0/b/onlybooks-3a802.appspot.com/o/documents%2FHarry-Potter-e-a-pedra-filosofal.pdf?alt=media&token=e9741440-df9d-4049-982e-44c2108baea6"
//        val file = File(applicationContext.getFileStreamPath(path), path)
//
//        println(file.name)


        val request = DownloadManager.Request(path.toUri())
        val title = URLUtil.guessFileName(path, null, null)
        request.setTitle(title)
        request.setDescription("Baixando")
//        val cookie: String = CookieManager.getInstance().getCookie(path)
//        request.addRequestHeader("cookie", cookie)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val enqueue = downloadManager.enqueue(request)

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            title
        )



        println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))

        Handler(Looper.getMainLooper()).postDelayed({
            binding.pdfView.fromFile(file)
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
        }, 3000)


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun buscarArquivo(fileName: String) {
        val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager

        val storageVolumes = storageManager.getStorageVolume(fileName.toUri())

//        List<StorageVolume> storageVolumes = storageManager.getStorageVolumes()
//
//        StorageVolume storageVolume = storageVolumes.get(0)
//
//        File file = new File(storageVolume.getDirectory().getPath()) + "/Download/" + stringFileName)


    }
}