package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.ChapterModel
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

    private val _getChapter = MutableLiveData<StateResource<String>>()
    val getChapter: LiveData<StateResource<String>> = _getChapter

    fun postChapter(chapter: ChapterModel, idEbook: Int, isEbook: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = chapterRepository.postChapter(chapter, idEbook, isEbook)
            _chapterResponse.postValue(handleResponse(response))
        }

    fun getChapter(numChapter: Int, idEbook: Int, isEbook: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = chapterRepository.getChapter(numChapter, idEbook, isEbook)
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