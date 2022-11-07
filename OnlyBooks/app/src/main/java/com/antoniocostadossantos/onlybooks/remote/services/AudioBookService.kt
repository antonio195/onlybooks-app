package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.model.EbookModel
import retrofit2.Response
import retrofit2.http.*


interface AudioBookService {

    @GET("audiobook/get-suggestions")
    suspend fun getSuggestions(): Response<List<AudioBookModel>>

    @PATCH("/audiobook/update-audiobook/{idAudioBook}")
    suspend fun updateAudioBook(
        @Body audioBook: AudioBookModel?,
        @Path("idAudioBook") idAudioBook: Int
    ): Response<String>

    @POST("/ebook/create-ebook/{idUser}")
    suspend fun postEbook(
        @Body ebook: EbookModel?,
        @Path("idUser") idUser: Int
    ): Response<String>

}