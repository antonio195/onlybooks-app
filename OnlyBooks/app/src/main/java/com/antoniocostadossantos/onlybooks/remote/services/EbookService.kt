package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.EbookModel
import retrofit2.Response
import retrofit2.http.GET


interface EbookService {

    @GET("ebook/get-suggestions")
    suspend fun getSuggestions(): Response<List<EbookModel>>

}