package com.antoniocostadossantos.onlybooks.remote.services

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface LibraryService {

    @GET("/library/exists-audiobook-in-library/{idAudioBook}/{idUser}")
    suspend fun existsInLibrary(
        @Path("idAudioBook") idAudioBook: Int,
        @Path("idUser") idUser: Int
    ): Response<Boolean>

    @DELETE("/library/delete-audiobook-library-mobile/{idAudioBook}/{idUser}")
    suspend fun deleteAudioBookInLibrary(
        @Path("idAudioBook") idAudioBook: Int,
        @Path("idUser") idUser: Int
    ): Response<Boolean>

    @POST("/library/add-audibook-library/{idAudioBook}/{idUser}")
    suspend fun addInLibrary(
        @Path("idAudioBook") idAudioBook: Int,
        @Path("idUser") idUser: Int
    ): Response<String>

}