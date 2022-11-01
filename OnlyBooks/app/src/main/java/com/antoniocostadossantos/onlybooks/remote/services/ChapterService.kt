package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ChapterService {

    @POST("/chapter-mobile/post-chapter-ebook-mobile/")
    suspend fun postChapter(
        @Body ebook: ChapterEbookMobile?
    ): Response<String>

    @GET("/chapter-mobile/get-chapters-ebook-mobile-by-idEbook/{idEbook}/")
    suspend fun getChapter(
        @Path("idEbook") idEbook: Int
    ): Response<List<ChapterEbookMobile>>

}