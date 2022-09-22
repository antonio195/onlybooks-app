package com.antoniocostadossantos.onlybooks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antoniocostadossantos.onlybooks.R

class EbookSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ebook_submission)

        supportActionBar?.hide()
    }

    fun uploadImage() {
    }
}