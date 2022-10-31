package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.EbookService

class EbookRepository : BaseRepository() {

    private val api = RetrofitClient.getService(EbookService::class.java)

    suspend fun getSuggestions() = api.getSuggestions()

    suspend fun updateEbook(ebook: EbookModel, idEbook: Int) = api.updateEbook(ebook, idEbook)

}