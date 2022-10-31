package com.antoniocostadossantos.onlybooks.model

data class AudioBookModel(
    val authorAudioBook: String,
    val classificacao: String,
    val coAuthorAudioBook: String,
    val descricao: String,
    val isEbook: Boolean,
    val genre1AudioBook: String,
    val genre2AudioBook: String,
    val genreAudioBook: String,
    val idAudioBook: Int,
    val idUsuario: UserModel,
    val nameAudioBook: String,
    val narrador2AudioBook: String,
    val narradorAudioBook: String,
    val precoAudioBook: Int,
    val statusAudioBook: Boolean,
    val urlAudioBook: String
)