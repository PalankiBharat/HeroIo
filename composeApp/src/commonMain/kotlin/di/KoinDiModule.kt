package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val sharedModule = module {
 /*   single { AppRepository(get(), get()) }
    single { SuperheroApi() }
    single { provideRealm() }
    single { SuperheroListingViewModel(get()) }
    platformModule()*/

}

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(sharedModule)
    }

fun initKoin() = initKoin() {}


