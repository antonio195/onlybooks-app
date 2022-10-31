package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.remote.repository.EbookRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class EbookViewModel(
    private val ebookRepository: EbookRepository
) : ViewModel() {

    init {
        getSuggestions()
    }

    private val _suggestions = MutableLiveData<StateResource<List<EbookModel>>>()
    val suggestions: LiveData<StateResource<List<EbookModel>>> = _suggestions

    private val _updateEbook = MutableLiveData<StateResource<String>>()
    val updateEbook: LiveData<StateResource<String>> = _updateEbook

    fun getSuggestions() = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getSuggestions()
        _suggestions.postValue(handleResponse(response))
    }

    fun updateEbook(ebookModel: EbookModel, idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.updateEbook(ebookModel, idEbook)
        _updateEbook.postValue(handleResponse(response))
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