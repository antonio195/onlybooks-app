package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.LibraryService

class LibraryRepository : BaseRepository() {

    private val api = RetrofitClient.getService(LibraryService::class.java)

    suspend fun existsAubioBookInLibrary(idUser: Int, idAudioBook: Int) =
        api.audioBookExistsInLibrary(idAudioBook, idUser)

    suspend fun deleteAudioBookInLibrary(idUser: Int, idAudioBook: Int) =
        api.deleteAudioBookInLibrary(idAudioBook, idUser)

    suspend fun addAudioBookInLibrary(idUser: Int, idAudioBook: Int) =
        api.addAudioBookInLibrary(idAudioBook, idUser)

    suspend fun countAudioBookForId(idUser: Int) = api.countAudioBookForId(idUser)

    suspend fun existsEbookInLibrary(idUser: Int, idEbook: Int) =
        api.ebookExistsInLibrary(idEbook, idUser)

    suspend fun deleteEbookInLibrary(idUser: Int, idEbook: Int) =
        api.deleteEbookInLibrary(idEbook, idUser)

    suspend fun addEbookInLibrary(idUser: Int, idEbook: Int) =
        api.addEbookInLibrary(idEbook, idUser)

    suspend fun countEbookForId(idUser: Int) = api.countEbookForId(idUser)
}