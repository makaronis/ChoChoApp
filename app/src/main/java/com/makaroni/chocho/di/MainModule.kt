package com.makaroni.chocho.di

import com.makaroni.chocho.features.account.domain.AuthRepository
import com.makaroni.chocho.features.account.domain.FirebaseAuthRepositoryImpl
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.data.db.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val module = module {
        single { AppDatabase.createDatabase(get()) }
        single<AuthRepository> { FirebaseAuthRepositoryImpl() }
        viewModel { AuthViewModel(get()) }
    }
}