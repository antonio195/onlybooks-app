package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.AudioBookModel
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

    @POST("/audiobook/create-audiobook/{idUser}")
    suspend fun postAudioBook(
        @Body audioBook: AudioBookModel?,
        @Path("idUser") idUser: Int
    ): Response<String>

}