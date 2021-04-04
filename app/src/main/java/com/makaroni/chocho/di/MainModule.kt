package com.makaroni.chocho.di

import com.makaroni.chocho.data.db.AppDatabase
import org.koin.dsl.module

object MainModule {
    val module = module {
        single { AppDatabase.createDatabase(get()) }
    }
}