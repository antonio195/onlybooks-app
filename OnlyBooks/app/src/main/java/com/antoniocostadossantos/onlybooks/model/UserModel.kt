package com.antoniocostadossantos.onlybooks.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("description")
    val descricao: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("headerURL")
    val header: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("isAdm")
    val isAdm: Boolean,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("photoURL")
    val photo: String,

    @SerializedName("senha")
    val senha: String
)