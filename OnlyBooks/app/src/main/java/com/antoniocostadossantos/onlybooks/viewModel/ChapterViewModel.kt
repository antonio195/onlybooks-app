package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.ChapterEbookMobile
import com.antoniocostadossantos.onlybooks.remote.repository.ChapterRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ChapterViewModel(
    private val chapterRepository: ChapterRepository
) : ViewModel() {

    private val _chapterResponse = MutableLiveData<StateResource<String>>()
    val chapterResponse: LiveData<StateResource<String>> = _chapterResponse

    private val _getChapter = MutableLiveData<StateResource<List<ChapterEbookMobile>>>()
    val getChapter: LiveData<StateResource<List<ChapterEbookMobile>>> = _getChapter

    fun postChapter(chapter: ChapterEbookMobile) = viewModelScope.launch(Dispatchers.IO) {
        val response = chapterRepository.postChapter(chapter)
        _chapterResponse.postValue(handleResponse(response))
    }

    fun getChapter(idEbook: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = chapterRepository.getChapter(idEbook)
            _getChapter.postValue(handleResponse(response))
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