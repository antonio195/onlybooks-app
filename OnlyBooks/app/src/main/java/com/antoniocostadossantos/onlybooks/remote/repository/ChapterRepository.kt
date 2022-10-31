package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.ChapterModel
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.ChapterService

class ChapterRepository : BaseRepository() {

    private val api = RetrofitClient.getService(ChapterService::class.java)

    suspend fun postChapter(chapter: ChapterModel, idEbook: Int, isEbook: Boolean) =
        api.postChapter(chapter, isEbook, idEbook)

    suspend fun getChapter(numChapter: Int, idEbook: Int, isEbook: Boolean) =
        api.getChapter(numChapter, idEbook, isEbook)

}