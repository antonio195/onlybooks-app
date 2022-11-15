package com.antoniocostadossantos.onlybooks.dependencyInjection

import com.antoniocostadossantos.onlybooks.remote.repository.LibraryRepository
import com.antoniocostadossantos.onlybooks.viewModel.LibraryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val libraryDependency = module {

    single {
        LibraryRepository()
    }

    viewModel {
        LibraryViewModel(
            libraryRepository = get()
        )
    }
}