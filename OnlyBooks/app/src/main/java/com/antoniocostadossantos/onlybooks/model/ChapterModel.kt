package com.antoniocostadossantos.onlybooks.model

data class ChapterModel(
    val idAudioBook: AudioBookModel,
    val idChapter: Int,
    val idEbook: EbookModel,
    val nameChapter: String,
    val numChapter: Int,
    val urlChapter: String
)