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

    private val _postEbook = MutableLiveData<StateResource<String>>()
    val postEbook: LiveData<StateResource<String>> = _postEbook

    private val _spotlightWeek = MutableLiveData<StateResource<EbookModel>>()
    val spotlightWeek: LiveData<StateResource<EbookModel>> = _spotlightWeek

    private val _myEbooks = MutableLiveData<StateResource<List<EbookModel>>>()
    val myEbooks: LiveData<StateResource<List<EbookModel>>> = _myEbooks

    private val _myEbooksInLibrary = MutableLiveData<StateResource<List<EbookModel>>>()
    val myEbooksInLibrary: LiveData<StateResource<List<EbookModel>>> = _myEbooksInLibrary

    private val _deleteEbook = MutableLiveData<StateResource<String>>()
    val deleteEbook: LiveData<StateResource<String>> = _deleteEbook

    private val _views = MutableLiveData<StateResource<Any>>()
    val views: LiveData<StateResource<Any>> = _views

    private val _likes = MutableLiveData<StateResource<Any>>()
    val likes: LiveData<StateResource<Any>> = _likes

    private val _searchEbook = MutableLiveData<StateResource<List<EbookModel>>>()
    val searchEbook: LiveData<StateResource<List<EbookModel>>> = _searchEbook

    fun getSuggestions() = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getSuggestions()
        _suggestions.postValue(handleResponse(response))
    }

    fun updateEbook(ebookModel: EbookModel, idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.updateEbook(ebookModel, idEbook)
        _updateEbook.postValue(handleResponse(response))
    }

    fun postEbook(ebookModel: EbookModel, idUser: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.postEbook(ebookModel, idUser)
        _postEbook.postValue(handleResponse(response))
    }

    fun getSpotlightWeek() = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getSpotlightWeek()
        _spotlightWeek.postValue(handleResponse(response))
    }

    fun getMyEbooks(idUsuario: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getMyEbooks(idUsuario)
        _myEbooks.postValue(handleResponse(response))
    }

    fun getMyEbooksInLibrary(idUsuario: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getMyEbooksInLibrary(idUsuario)
        _myEbooksInLibrary.postValue(handleResponse(response))
    }

    fun deleteEbook(idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.deleteEbook(idEbook)
        _deleteEbook.postValue(handleResponse(response))
    }

    fun getViews(idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getViews(idEbook)
        _views.postValue(handleResponse(response))
    }

    fun getLikes(idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.getLikes(idEbook)
        _likes.postValue(handleResponse(response))
    }

    fun searchEbook(text: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = ebookRepository.searchEbook(text)
        _searchEbook.postValue(handleResponse(response))
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