package com.antoniocostadossantos.onlybooks.repository

import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.UserService

class UserRepository : BaseRepository() {

    private val api = RetrofitClient.getService(UserService::class.java)

    suspend fun login(email: String, password: String) = api.login(email, password)

}