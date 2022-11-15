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

    private val _existsInLibrary = MutableLiveData<StateResource<Boolean>>()
    val existsInLibrary: LiveData<StateResource<Boolean>> = _existsInLibrary

    private val _deleteInLibrary = MutableLiveData<StateResource<Boolean>>()
    val deleteInLibrary: LiveData<StateResource<Boolean>> = _deleteInLibrary

    private val _addInLibrary = MutableLiveData<StateResource<Any>>()
    val addInLibrary: LiveData<StateResource<Any>> = _addInLibrary

    fun existsInLibrary(idUser: Int, idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.existsInLibrary(idUser, idAudioBook)
        _existsInLibrary.postValue(handleResponse(response))
    }

    fun deleteInLibrary(idUser: Int, idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.deleteInLibrary(idUser, idAudioBook)
        _deleteInLibrary.postValue(handleResponse(response))
    }

    fun addInLibrary(idUser: Int, idAudioBook: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = libraryRepository.addInLibrary(idUser, idAudioBook)
        _addInLibrary.postValue(handleResponse(response))
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