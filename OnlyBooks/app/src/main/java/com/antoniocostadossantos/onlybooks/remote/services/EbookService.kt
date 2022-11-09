package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.EbookModel
import retrofit2.Response
import retrofit2.http.*


interface EbookService {

    @GET("ebook/get-suggestions")
    suspend fun getSuggestions(): Response<List<EbookModel>>

    @PATCH("/ebook/update-ebook/{idEbook}")
    suspend fun updateEbook(
        @Body ebook: EbookModel?,
        @Path("idEbook") idEbook: Int
    ): Response<String>

    @POST("/ebook/create-ebook/{idUser}")
    suspend fun postEbook(
        @Body ebook: EbookModel?,
        @Path("idUser") idUser: Int
    ): Response<String>

    @GET("/ebook/get-spotlight-week")
    suspend fun getSpotlightWeek(): Response<EbookModel>

}