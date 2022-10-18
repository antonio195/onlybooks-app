package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.ListUserModel
import com.antoniocostadossantos.onlybooks.model.UserModel
import com.antoniocostadossantos.onlybooks.model.UserModelDTO
import com.antoniocostadossantos.onlybooks.repository.UserRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _login = MutableLiveData<StateResource<ListUserModel>>()
    val login: LiveData<StateResource<ListUserModel>> = _login

    private val _jesuscristo = MutableLiveData<UserModel>()
    val jesuscristo: LiveData<UserModel> = _jesuscristo

    private val _dataCache = MutableLiveData<StateResource<ListUserModel>>()
    val dataCache: LiveData<StateResource<ListUserModel>> = _dataCache

    private val _register = MutableLiveData<StateResource<String>>()
    val register: LiveData<StateResource<String>> = _register

    private val _recoveryPassword = MutableLiveData<StateResource<String>>()
    val recoveryPassword: LiveData<StateResource<String>> = _recoveryPassword

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.login(email, password)
        _login.postValue(handleResponse(response))
    }

    fun register(userDTO: UserModelDTO) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.register(userDTO)
        _register.postValue(handleResponse(response))
    }

    fun recoveryPassword(email: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.recoveryPassword(email)
        _recoveryPassword.postValue(handleResponse(response))
    }

    fun jesuscristo(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userRepository.login(email, password)
        _jesuscristo.postValue(response.body()?.items?.get(0))
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