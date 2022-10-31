package com.antoniocostadossantos.onlybooks.dependencyInjection

import com.antoniocostadossantos.onlybooks.remote.repository.ChapterRepository
import com.antoniocostadossantos.onlybooks.viewModel.ChapterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chapterDependency = module {

    single {
        ChapterRepository()
    }

    viewModel {
        ChapterViewModel(
            chapterRepository = get()
        )
    }
}