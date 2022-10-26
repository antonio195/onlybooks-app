package com.antoniocostadossantos.onlybooks.model

data class EbookModel(
    val authorEbook: String,
    val classificacao: String,
    val coAuthorEbook: String,
    val descricao: String,
    val ebook: Boolean,
    val genre1Ebook: String,
    val genre2Ebook: String,
    val genreEbook: String,
    val idEbook: Int,
    val idUsuario: UserModel,
    val nameEbook: String,
    val preco: Double,
    val qtdChapters: Int,
    val statusEbook: Boolean,
    val url: String
)