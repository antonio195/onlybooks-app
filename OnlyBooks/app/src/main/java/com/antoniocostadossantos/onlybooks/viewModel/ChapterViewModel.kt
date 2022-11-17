package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.ChapterAudioBookMobile
import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import com.antoniocostadossantos.onlybooks.remote.repository.ChapterRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ChapterViewModel(
    private val chapterRepository: ChapterRepository
) : ViewModel() {

    private val _chapterEbookResponse = MutableLiveData<StateResource<String>>()
    val chapterEbookResponse: LiveData<StateResource<String>> = _chapterEbookResponse

    private val _getChapterEbook = MutableLiveData<StateResource<List<ChapterEbookMobile>>>()
    val getChapterEbook: LiveData<StateResource<List<ChapterEbookMobile>>> = _getChapterEbook

    private val _chapterAudioBookResponse = MutableLiveData<StateResource<String>>()
    val chapterAudioBookResponse: LiveData<StateResource<String>> = _chapterAudioBookResponse

    private val _getChapterAudioBook =
        MutableLiveData<StateResource<List<ChapterAudioBookMobile>>>()
    val getChapterAudioBook: LiveData<StateResource<List<ChapterAudioBookMobile>>> =
        _getChapterAudioBook

    fun postChapterEbook(chapter: ChapterEbookMobile) = viewModelScope.launch(Dispatchers.IO) {
        val response = chapterRepository.postChapterEbook(chapter)
        _chapterEbookResponse.postValue(handleResponse(response))
    }

    fun getChapterEbook(idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = chapterRepository.getChapterEbook(idEbook)
        _getChapterEbook.postValue(handleResponse(response))
    }

    fun postChapterAudioBook(chapter: ChapterAudioBookMobile) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = chapterRepository.postChapterAudioBook(chapter)
            _chapterAudioBookResponse.postValue(handleResponse(response))
        }

    fun getChapterAudioBook(idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = chapterRepository.getChapterAudioBook(idAudioBook)
        _getChapterAudioBook.postValue(handleResponse(response))
    }

    private fun <T> handleResponse(response: Response<T>): StateResource<T> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return StateResource.Success(values)
            }
        }
        return StateResource.Error(response.message())
    }

}