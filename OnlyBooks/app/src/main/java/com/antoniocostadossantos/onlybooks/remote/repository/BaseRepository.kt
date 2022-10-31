package com.antoniocostadossantos.onlybooks.remote.repository

import com.antoniocostadossantos.onlybooks.remote.repository.listener.APIListener
import com.google.gson.Gson
import retrofit2.Response

open class BaseRepository {

    fun failResponse(str: String): String {
        return Gson().fromJson(str, String::class.java)
    }

    fun <T> handleResponse(response: Response<T>, listener: APIListener<T>) {
        if (response.code() == 200) {
            response.body()?.let { listener.onSucess(it) }
        } else {
            listener.onFailure(failResponse(response.errorBody()!!.string()))
        }
    }



}