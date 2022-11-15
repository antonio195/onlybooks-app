package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.LibraryService

class LibraryRepository : BaseRepository() {

    private val api = RetrofitClient.getService(LibraryService::class.java)

    suspend fun existsInLibrary(idUser: Int, idAudioBook: Int) =
        api.existsInLibrary(idAudioBook, idUser)

    suspend fun deleteInLibrary(idUser: Int, idAudioBook: Int) =
        api.deleteAudioBookInLibrary(idAudioBook, idUser)

    suspend fun addInLibrary(idUser: Int, idAudioBook: Int) =
        api.addInLibrary(idAudioBook, idUser)

}