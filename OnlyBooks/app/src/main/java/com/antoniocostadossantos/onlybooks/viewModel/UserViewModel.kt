package com.antoniocostadossantos.onlybooks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.onlybooks.model.ListUserModel
import com.antoniocostadossantos.onlybooks.model.UserModelDTO
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.repository.UserRepository
import com.antoniocostadossantos.onlybooks.util.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _login = MutableLiveData<StateResource<ListUserModel>>()
    val login: LiveData<StateResource<ListUserModel>> = _login

    private val _register = MutableLiveData<StateResource<String>>()
    val register: LiveData<StateResource<String>> = _register

    fun login(email: String, password: String) = viewModelScope.launch {
        val response = userRepository.login(email, password)
        _login.value = handleResponse(response)
    }

    fun register(userDTO: UserModelDTO) = viewModelScope.launch {
        val response = userRepository.register(userDTO)
        _register.value = handleResponse(response)
    }

    private fun <T> handleResponse(response: Response<T>): StateResource<T> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return StateResource.Success(values)
            }
        }
        return StateResource.Error(response.message())
    }

//    private fun handleResponse(response: Response<NewsResponse>): StateResource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { values ->
//                return StateResource.Success(values)
//            }
//        }
//        return StateResource.Error(response.message())
//    }

}