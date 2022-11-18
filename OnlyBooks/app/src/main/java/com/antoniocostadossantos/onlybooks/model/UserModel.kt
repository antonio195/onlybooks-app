package com.antoniocostadossantos.onlybooks.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("description")
    var descricao: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("headerURL")
    var header: String,

    @SerializedName("id")
    var id: Int,

    @SerializedName("isAdm")
    var isAdm: Boolean,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("photoURL")
    var photo: String,

    @SerializedName("senha")
    var senha: String
)