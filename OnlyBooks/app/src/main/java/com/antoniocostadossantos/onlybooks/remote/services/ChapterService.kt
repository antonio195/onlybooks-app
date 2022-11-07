package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.ChapterAudioBookMobile
import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ChapterService {

    @POST("/chapter-mobile/post-chapter-ebook-mobile/")
    suspend fun postChapterEbook(
        @Body ebook: ChapterEbookMobile?
    ): Response<String>

    @GET("/chapter-mobile/get-chapters-ebook-mobile-by-idEbook/{idEbook}/")
    suspend fun getChapterEbook(
        @Path("idEbook") idEbook: Int
    ): Response<List<ChapterEbookMobile>>

    @POST("/chapter-mobile/post-chapter-audiobook-mobile/")
    suspend fun postChapterAudioBook(
        @Body audioBook: ChapterAudioBookMobile?
    ): Response<String>

    @GET("/chapter-mobile/get-chapters-audiobook-mobile-by-idAudioBook/{idAudioBook}/")
    suspend fun getChapterAudioEbook(
        @Path("idAudioBook") idAudioBook: Int
    ): Response<List<ChapterAudioBookMobile>>

}