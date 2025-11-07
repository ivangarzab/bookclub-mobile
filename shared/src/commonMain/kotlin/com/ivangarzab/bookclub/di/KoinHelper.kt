package com.ivangarzab.bookclub.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

/**
 * The purpose of this expected [org.koin.core.Koin] module is to hold all of the platform-specific dependencies.
 */
expect val platformDataModule: Module

/**
 * The purpose of this function is to initialize [org.koin.core.Koin] and all of its relevant modules.
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformDataModule
    )
}

fun initKoin() = initKoin {}