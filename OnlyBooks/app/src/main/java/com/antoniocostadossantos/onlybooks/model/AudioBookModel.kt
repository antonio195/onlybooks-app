package com.antoniocostadossantos.onlybooks.model

data class AudioBookModel(
    var authorAudioBook: String,
    var classificacao: String,
    var coAuthorAudioBook: String,
    var descricao: String,
    var isEbook: Boolean,
    var genre1AudioBook: String,
    var genre2AudioBook: String,
    var genreAudioBook: String,
    var idAudioBook: Int,
    var idUsuario: UserModel,
    var nameAudioBook: String,
    var narrador2AudioBook: String,
    var narradorAudioBook: String,
    var precoAudioBook: Int,
    var statusAudioBook: Boolean,
    var urlAudioBook: String
)