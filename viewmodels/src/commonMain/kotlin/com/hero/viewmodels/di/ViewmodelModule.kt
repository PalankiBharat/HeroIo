package com.hero.viewmodels.di

import com.hero.viewmodels.vms.SuperheroListingViewmodel
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewmodelModule = module {
    viewModelOf(::SuperheroListingViewmodel)
    viewModelOf(::SuperheroDetailsViewmodel)
}