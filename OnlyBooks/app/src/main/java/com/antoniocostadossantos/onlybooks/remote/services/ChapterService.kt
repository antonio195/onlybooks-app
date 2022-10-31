package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.ChapterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ChapterService {

    @POST("/ebook/update-ebook/{isEbook}/{idEbook}")
    suspend fun postChapter(
        @Body ebook: ChapterModel?,
        @Path("isEbook") isEbook: Boolean,
        @Path("idEbook") idEbook: Int
    ): Response<String>

    @GET("/ebook/view-chapter/{numChapter}/{idEbook}/{isEbook}")
    suspend fun getChapter(
        @Path("idEbook") numChapter: Int,
        @Path("idEbook") idEbook: Int,
        @Path("isEbook") isEbook: Boolean,
    ): Response<String>

}