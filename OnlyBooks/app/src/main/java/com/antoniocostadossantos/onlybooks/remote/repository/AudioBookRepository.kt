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

    suspend fun getMyEbooks(idUser: Int) = api.getMyAudioBooks(idUser)

    suspend fun getMyAudioBookssInLibrary(idUser: Int) = api.getMyAudioBookssInLibrary(idUser)

    suspend fun deleteAudioBook(idAudioBook: Int) = api.deleteAudioBook(idAudioBook)

}