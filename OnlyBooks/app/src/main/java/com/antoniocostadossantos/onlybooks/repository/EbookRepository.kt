package com.antoniocostadossantos.onlybooks.repository

import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.EbookService

class EbookRepository : BaseRepository() {

    private val api = RetrofitClient.getService(EbookService::class.java)

    suspend fun getSuggestions() = api.getSuggestions()

}