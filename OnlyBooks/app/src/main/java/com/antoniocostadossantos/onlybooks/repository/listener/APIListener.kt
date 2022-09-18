package com.antoniocostadossantos.onlybooks.repository.listener

interface APIListener<T> {
    fun onSucess(result: T)
    fun onFailure(message: String)

}