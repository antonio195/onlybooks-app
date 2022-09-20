package com.antoniocostadossantos.onlybooks.remote

import com.antoniocostadossantos.onlybooks.constants.Constants.Companion.URL_DEV
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext


class RetrofitClient {
    companion object {
        private val retrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()


            val gson = GsonBuilder().setLenient().create()

            Retrofit.Builder()
                .baseUrl(URL_DEV)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return retrofit.create(serviceClass)
        }
    }
}