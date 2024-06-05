package com.hero.io

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class HeroIoApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@HeroIoApplication)
        }
    }
}