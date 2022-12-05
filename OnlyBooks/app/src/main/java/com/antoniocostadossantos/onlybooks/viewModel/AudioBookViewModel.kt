package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.remote.repository.AudioBookRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AudioBookViewModel(
    private val audioBookRepository: AudioBookRepository
) : ViewModel() {

    init {
        getSuggestions()
    }

    private val _suggestions = MutableLiveData<StateResource<List<AudioBookModel>>>()
    val suggestions: LiveData<StateResource<List<AudioBookModel>>> = _suggestions

    private val _updateAudioBook = MutableLiveData<StateResource<String>>()
    val updateAudioBook: LiveData<StateResource<String>> = _updateAudioBook

    private val _postAudioBook = MutableLiveData<StateResource<String>>()
    val postAudioBook: LiveData<StateResource<String>> = _postAudioBook

    private val _myAudioBooks = MutableLiveData<StateResource<List<AudioBookModel>>>()
    val myAudioBooks: LiveData<StateResource<List<AudioBookModel>>> = _myAudioBooks

    private val _myAudioBooksInLibrary = MutableLiveData<StateResource<List<AudioBookModel>>>()
    val myAudioBooksInLibrary: LiveData<StateResource<List<AudioBookModel>>> =
        _myAudioBooksInLibrary

    private val _deleteAudioBook = MutableLiveData<StateResource<String>>()
    val deleteAudioBook: LiveData<StateResource<String>> = _deleteAudioBook

    private val _views = MutableLiveData<StateResource<Any>>()
    val views: LiveData<StateResource<Any>> = _views

    private val _likes = MutableLiveData<StateResource<Any>>()
    val likes: LiveData<StateResource<Any>> = _likes

    private val _searchAudioBook = MutableLiveData<StateResource<List<AudioBookModel>>>()
    val searchAudioBook: LiveData<StateResource<List<AudioBookModel>>> = _searchAudioBook

    fun getSuggestions() = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.getSuggestions()
        _suggestions.postValue(handleResponse(response))
    }

    fun updateAudioBook(audioBook: AudioBookModel, idAudioBook: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = audioBookRepository.updateAudioBook(audioBook, idAudioBook)
            _updateAudioBook.postValue(handleResponse(response))
        }

    fun postAudioBook(audioBook: AudioBookModel, idUser: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = audioBookRepository.postAudioBook(audioBook, idUser)
            _postAudioBook.postValue(handleResponse(response))
        }

    fun getMyEbooks(idUsuario: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.getMyEbooks(idUsuario)
        _myAudioBooks.postValue(handleResponse(response))
    }

    fun getMyAudioBookssInLibrary(idUsuario: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.getMyAudioBookssInLibrary(idUsuario)
        _myAudioBooksInLibrary.postValue(handleResponse(response))
    }

    fun deleteAudioBook(idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.deleteAudioBook(idAudioBook)
        _deleteAudioBook.postValue(handleResponse(response))
    }

    fun getViews(idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.getViews(idAudioBook)
        _views.postValue(handleResponse(response))
    }

    fun getLikes(idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.getLikes(idAudioBook)
        _likes.postValue(handleResponse(response))
    }

    fun searchAudioBook(text: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = audioBookRepository.searchAudioBook(text)
        _searchAudioBook.postValue(handleResponse(response))
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