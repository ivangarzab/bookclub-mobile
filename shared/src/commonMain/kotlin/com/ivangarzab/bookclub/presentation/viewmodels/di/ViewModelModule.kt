package com.ivangarzab.bookclub.presentation.viewmodels.di

import com.ivangarzab.bookclub.presentation.viewmodels.club.ClubDetailsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::ClubDetailsViewModel)
}