package com.example.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coroutineModule = module {
    factory {
        AppCoroutineDispatcher(
            Dispatchers.Main,
            Dispatchers.IO,
            Dispatchers.Default
        )
    }
}

class AppCoroutineDispatcher(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)