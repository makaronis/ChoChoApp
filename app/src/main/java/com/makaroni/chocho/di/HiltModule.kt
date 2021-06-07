package com.makaroni.chocho.di

import com.makaroni.chocho.features.account.domain.AuthRepository
import com.makaroni.chocho.features.account.domain.FirebaseAuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HiltModule {
    @Binds
    abstract fun bindsAuthRepository(
        repositoryImpl: FirebaseAuthRepositoryImpl
    ): AuthRepository
}

@Module
@InstallIn(ViewModelComponent::class)
object RepoImplModule {
    @Provides
    fun provideFirebaseAuthRepositoryImpl() = FirebaseAuthRepositoryImpl()
}