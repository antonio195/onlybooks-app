package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.ChapterAudioBookMobile
import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.ChapterService

class ChapterRepository : BaseRepository() {

    private val api = RetrofitClient.getService(ChapterService::class.java)

    suspend fun postChapterEbook(chapter: ChapterEbookMobile) = api.postChapterEbook(chapter)

    suspend fun getChapterEbook(idEbookk: Int) = api.getChapterEbook(idEbookk)

    suspend fun postChapterAudioBook(chapter: ChapterAudioBookMobile) =
        api.postChapterAudioBook(chapter)

    suspend fun getChapterAudioBook(idAudioBook: Int) = api.getChapterAudioEbook(idAudioBook)

}