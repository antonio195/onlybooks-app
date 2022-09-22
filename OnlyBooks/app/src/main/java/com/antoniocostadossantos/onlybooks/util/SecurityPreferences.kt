package com.antoniocostadossantos.onlybooks.util

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("UserData", Context.MODE_PRIVATE)


    fun getEmail(): String? {
        return preferences.getString("email", "")
    }

    fun setEmail(email: String) {
        preferences.edit().putString("email,", email)
    }

    fun getName(): String? {
        return preferences.getString("email", "")
    }

    fun setName(name: String) {
        preferences.edit().putString("name,", name)
    }

    fun getId(): String? {
        return preferences.getString("id", "")
    }

    fun setId(id: String) {
        preferences.edit().putString("id,", id)
    }

    fun getPassword(): String? {
        return preferences.getString("password", "")
    }

    fun setPassword(password: String) {
        preferences.edit().putString("id,", password)
    }

    fun getHeader(): String? {
        return preferences.getString("header", "")
    }

    fun setHeader(header: String) {
        preferences.edit().putString("header,", header)
    }

    fun getPhoto(): String? {
        return preferences.getString("photo", "")
    }

    fun setPhoto(photo: String) {
        preferences.edit().putString("photo,", photo)
    }

    fun getDescription(): String? {
        return preferences.getString("description", "")
    }

    fun setDescription(description: String) {
        preferences.edit().putString("description,", description)
    }

}