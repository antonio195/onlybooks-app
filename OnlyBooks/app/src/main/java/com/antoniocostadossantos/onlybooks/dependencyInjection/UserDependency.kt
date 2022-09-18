package com.antoniocostadossantos.onlybooks.dependencyInjection

import android.app.Application
import android.content.SharedPreferences
import com.antoniocostadossantos.onlybooks.repository.UserRepository
import com.antoniocostadossantos.onlybooks.viewModel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
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