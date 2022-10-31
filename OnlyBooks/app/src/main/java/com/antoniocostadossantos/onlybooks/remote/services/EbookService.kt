package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.EbookModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path


interface EbookService {

    @GET("ebook/get-suggestions")
    suspend fun getSuggestions(): Response<List<EbookModel>>

    @PATCH("/ebook/update-ebook/{idEbook}")
    suspend fun updateEbook(
        @Body ebook: EbookModel?,
        @Path("idEbook")
        idEbook: Int
    ): Response<String>

}