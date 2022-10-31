package com.antoniocostadossantos.onlybooks.remote.repository.listener

interface APIListener<T> {
    fun onSucess(result: T)
    fun onFailure(message: String)

}