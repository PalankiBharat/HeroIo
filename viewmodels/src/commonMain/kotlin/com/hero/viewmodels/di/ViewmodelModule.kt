package com.hero.viewmodels.di

import com.hero.data.di.daoModule
import com.hero.domain.di.domainModule
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module


@Module
@ComponentScan("com.hero.viewmodel")
class ViewmodelModule


val viewmodelModule = module {
    viewModelOf(::SuperheroDetailsViewmodel)
    includes(
        listOf(
            ViewmodelModule().module
        )
    )

}