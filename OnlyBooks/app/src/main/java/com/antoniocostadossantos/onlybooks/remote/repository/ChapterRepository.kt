package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.ChapterService

class ChapterRepository : BaseRepository() {

    private val api = RetrofitClient.getService(ChapterService::class.java)

    suspend fun postChapter(chapter: ChapterEbookMobile) = api.postChapter(chapter)


    suspend fun getChapter(idEbookk: Int) = api.getChapter(idEbookk)

}