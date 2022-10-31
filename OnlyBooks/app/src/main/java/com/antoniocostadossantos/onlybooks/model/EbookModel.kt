package com.antoniocostadossantos.onlybooks.model

data class EbookModel(
    var authorEbook: String,
    var classificacao: String,
    var coAuthorEbook: String,
    var descricao: String,
    var ebook: Boolean,
    var genre1Ebook: String,
    var genre2Ebook: String,
    var genreEbook: String,
    var idEbook: Int,
    var idUsuario: UserModel,
    var nameEbook: String,
    var preco: Double,
    var qtdChapters: Int,
    var statusEbook: Boolean,
    var url: String
)