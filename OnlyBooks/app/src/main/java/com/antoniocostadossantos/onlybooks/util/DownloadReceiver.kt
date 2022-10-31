package com.antoniocostadossantos.onlybooks.util

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.io.File

class DownloadReceiver : BroadcastReceiver() {

    private lateinit var file: File

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        when (action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                if (::file.isInitialized) {
                } else {
                    println("Arquivo não chegou!")
                    println("Arquivo não chegou!")
                    println("Arquivo não chegou!")
                    println("Arquivo não chegou!")
                }

            }
        }
    }

    fun setFile(file: File) {
        this.file = file
    }
}