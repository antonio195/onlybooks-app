package com.antoniocostadossantos.onlybooks.dependencyInjection

import com.antoniocostadossantos.onlybooks.remote.repository.AudioBookRepository
import com.antoniocostadossantos.onlybooks.viewModel.AudioBookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val audioBookDependency = module {

    single {
        AudioBookRepository()
    }

    viewModel {
        AudioBookViewModel(
            audioBookRepository = get()
        )
    }
}