package com.ivangarzab.bookclub.di

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * The purpose of this [org.koin.core.Koin] module is to hold all of the platform-specific
 * dependencies for Android.
 */
val androidDataModule = module {
    // Insert all Android-specific modules here
}

actual val platformDataModule: Module = androidDataModule