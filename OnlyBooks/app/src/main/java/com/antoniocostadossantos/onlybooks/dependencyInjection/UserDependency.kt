package com.antoniocostadossantos.onlybooks.dependencyInjection

import com.antoniocostadossantos.onlybooks.remote.repository.UserRepository
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val userDependency = module {

    single {
        UserRepository()
    }

    viewModel {
        UserViewModel(
            userRepository = get()
        )
    }
}