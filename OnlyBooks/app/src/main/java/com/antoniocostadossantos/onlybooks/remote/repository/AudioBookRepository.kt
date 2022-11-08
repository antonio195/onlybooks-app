package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.AudioBookService

class AudioBookRepository : BaseRepository() {

    private val api = RetrofitClient.getService(AudioBookService::class.java)

    suspend fun getSuggestions() = api.getSuggestions()

    suspend fun updateAudioBook(audioBook: AudioBookModel, idAudioBook: Int) =
        api.updateAudioBook(audioBook, idAudioBook)

    suspend fun postAudioBook(audioBook: AudioBookModel, idUser: Int) =
        api.postAudioBook(audioBook, idUser)

}