package di

import com.hero.data.di.daoModule
import com.hero.domain.di.domainModule
import com.hero.viewmodels.di.viewmodelModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(
            daoModule,
            domainModule,
            viewmodelModule
        )
    }

fun initKoin() = initKoin {}





