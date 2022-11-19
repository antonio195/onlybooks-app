package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.remote.repository.LibraryRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LibraryViewModel(
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    private val _existsAubioBookInLibrary = MutableLiveData<StateResource<Boolean>>()
    val existsAubioBookInLibrary: LiveData<StateResource<Boolean>> = _existsAubioBookInLibrary

    private val _deleteAubioBookInLibrary = MutableLiveData<StateResource<Boolean>>()
    val deleteAubioBookInLibrary: LiveData<StateResource<Boolean>> = _deleteAubioBookInLibrary

    private val _addAudioBookInLibrary = MutableLiveData<StateResource<Any>>()
    val addAudioBookInLibrary: LiveData<StateResource<Any>> = _addAudioBookInLibrary

    private val _countAudioBookForId = MutableLiveData<StateResource<Any>>()
    val countAudioBookForId: LiveData<StateResource<Any>> = _countAudioBookForId

    private val _existsEbookInLibrary = MutableLiveData<StateResource<Boolean>>()
    val existsEbookInLibrary: LiveData<StateResource<Boolean>> = _existsEbookInLibrary

    private val _deleteEbookInLibrary = MutableLiveData<StateResource<Boolean>>()
    val deleteEbookInLibrary: LiveData<StateResource<Boolean>> = _deleteEbookInLibrary

    private val _addEbookInLibrary = MutableLiveData<StateResource<Any>>()
    val addEbookInLibrary: LiveData<StateResource<Any>> = _addEbookInLibrary

    private val _countEbookForId = MutableLiveData<StateResource<Any>>()
    val countEbookForId: LiveData<StateResource<Any>> = _countEbookForId

    fun existsAubioBookInLibrary(idUser: Int, idAudioBook: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = libraryRepository.existsAubioBookInLibrary(idUser, idAudioBook)
            _existsAubioBookInLibrary.postValue(handleResponse(response))
        }

    fun deleteAubioBookInLibrary(idUser: Int, idAudioBook: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = libraryRepository.deleteAudioBookInLibrary(idUser, idAudioBook)
            _deleteAubioBookInLibrary.postValue(handleResponse(response))
        }

    fun addAudioBookInLibrary(idUser: Int, idAudioBook: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = libraryRepository.addAudioBookInLibrary(idUser, idAudioBook)
            _addAudioBookInLibrary.postValue(handleResponse(response))
        }

    fun countAudioBookForId(idUser: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.countAudioBookForId(idUser)
        _countAudioBookForId.postValue(handleResponse(response))
    }

    fun existsEbookInLibrary(idUser: Int, idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.existsEbookInLibrary(idUser, idEbook)
        _existsEbookInLibrary.postValue(handleResponse(response))
    }

    fun deleteEbookInLibrary(idUser: Int, idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.deleteEbookInLibrary(idUser, idEbook)
        _deleteEbookInLibrary.postValue(handleResponse(response))
    }

    fun addEbookInLibrary(idUser: Int, idEbook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.addEbookInLibrary(idUser, idEbook)
        _addEbookInLibrary.postValue(handleResponse(response))
    }

    fun countEbookForId(idUser: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.countEbookForId(idUser)
        _countEbookForId.postValue(handleResponse(response))
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