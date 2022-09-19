package com.antoniocostadossantos.onlybooks.remote.services

import com.antoniocostadossantos.onlybooks.model.ListUserModel
import com.antoniocostadossantos.onlybooks.model.UserModelDTO
import retrofit2.Response
import retrofit2.http.*


interface UserService {

    @GET("/users/user-login-mobile/{email}/{senha}")
    suspend fun login(
        @Path("email")
        email: String,
        @Path("senha")
        senha: String
    ): Response<ListUserModel>

    @Headers("Content-Type: application/json")
    @POST("/users/register")
    suspend fun createUser(
        @Body user: UserModelDTO?
    ): Response<String>

}