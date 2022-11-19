package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.EbookService

class EbookRepository : BaseRepository() {

    private val api = RetrofitClient.getService(EbookService::class.java)

    suspend fun getSuggestions() = api.getSuggestions()

    suspend fun updateEbook(ebook: EbookModel, idEbook: Int) = api.updateEbook(ebook, idEbook)

    suspend fun postEbook(ebook: EbookModel, idUser: Int) = api.postEbook(ebook, idUser)

    suspend fun getSpotlightWeek() = api.getSpotlightWeek()

    suspend fun getMyEbooks(idUser: Int) = api.getMyEbooks(idUser)

    suspend fun getMyEbooksInLibrary(idUser: Int) = api.getMyEbooksInLibrary(idUser)

    suspend fun deleteEbook(idEbook: Int) = api.deleteEbook(idEbook)

    suspend fun getViews(idEbook: Int) = api.getViews(idEbook)

    suspend fun getLikes(idEbook: Int) = api.getLikes(idEbook)
}