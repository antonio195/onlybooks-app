package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.model.UserModel
import com.antoniocostadossantos.onlybooks.model.UserModelDTO
import com.antoniocostadossantos.onlybooks.remote.RetrofitClient
import com.antoniocostadossantos.onlybooks.remote.services.UserService

class UserRepository : BaseRepository() {

    private val api = RetrofitClient.getService(UserService::class.java)

    suspend fun login(email: String, password: String) = api.login(email, password)

    suspend fun register(userDTO: UserModelDTO) = api.createUser(userDTO)

    suspend fun recoveryPassword(email: String) = api.recoveryPassword(email)

    suspend fun getUserById(idUser: Int) = api.getUserById(idUser)

    suspend fun updateUser(idUser: Int, userModel: UserModel) = api.updateUser(idUser, userModel)

}