package com.antoniocostadossantos.onlybooks.dependencyInjection

import com.antoniocostadossantos.onlybooks.repository.EbookRepository
import com.antoniocostadossantos.onlybooks.viewModel.EbookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ebookDependency = module {

    single {
        EbookRepository()
    }

    viewModel {
        EbookViewModel(
            ebookRepository = get()
        )
    }
}