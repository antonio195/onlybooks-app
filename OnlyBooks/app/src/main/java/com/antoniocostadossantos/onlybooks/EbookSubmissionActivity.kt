package com.antoniocostadossantos.onlybooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EbookSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ebook_submission)

        supportActionBar?.hide()
    }

    fun uploadImage() {
    }
}