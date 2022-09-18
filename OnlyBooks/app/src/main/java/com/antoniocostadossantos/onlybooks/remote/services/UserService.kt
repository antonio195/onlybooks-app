package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.ListUserModel
import retrofit2.Call
import com.antoniocostadossantos.onlybooks.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/users/user-login-mobile/{email}/{senha}")
    suspend fun login(
        @Path("email")
        email: String,
        @Path("senha")
        senha: String
    ): Response<ListUserModel>

}