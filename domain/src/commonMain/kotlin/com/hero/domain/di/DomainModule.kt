package com.hero.domain.di

import com.hero.data.di.daoModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.hero.domain")
class DomainModule


val domainModule = module {
    includes(
        listOf(
            DomainModule().module
        )
    )
}
