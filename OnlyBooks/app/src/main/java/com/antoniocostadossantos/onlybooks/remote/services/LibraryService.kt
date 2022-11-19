package com.antoniocostadossantos.onlybooks.remote.services

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface LibraryService {

    @GET("/library/exists-audiobook-in-library/{idAudioBook}/{idUser}")
    suspend fun audioBookExistsInLibrary(
        @Path("idAudioBook") idAudioBook: Int,
        @Path("idUser") idUser: Int
    ): Response<Boolean>

    @DELETE("/library/delete-audiobook-library-mobile/{idAudioBook}/{idUser}")
    suspend fun deleteAudioBookInLibrary(
        @Path("idAudioBook") idAudioBook: Int,
        @Path("idUser") idUser: Int
    ): Response<Boolean>

    @POST("/library/add-audibook-library/{idAudioBook}/{idUser}")
    suspend fun addAudioBookInLibrary(
        @Path("idAudioBook") idAudioBook: Int,
        @Path("idUser") idUser: Int
    ): Response<String>

    @GET("/library/count-audiobook-for-id/{idUser}")
    suspend fun countAudioBookForId(
        @Path("idUser") idUser: Int
    ): Response<String>

    @GET("/library/exists-ebook-in-library/{idEbook}/{idUser}")
    suspend fun ebookExistsInLibrary(
        @Path("idEbook") idEbook: Int,
        @Path("idUser") idUser: Int
    ): Response<Boolean>

    @DELETE("/library/delete-ebook-library-mobile/{idEbook}/{idUser}")
    suspend fun deleteEbookInLibrary(
        @Path("idEbook") idEbook: Int,
        @Path("idUser") idUser: Int
    ): Response<Boolean>

    @POST("/library/add-ebook-library/{idEbook}/{idUser}")
    suspend fun addEbookInLibrary(
        @Path("idEbook") idEbook: Int,
        @Path("idUser") idUser: Int
    ): Response<String>

    @GET("/library/count-ebook-for-id/{idUser}")
    suspend fun countEbookForId(
        @Path("idUser") idUser: Int
    ): Response<String>

}