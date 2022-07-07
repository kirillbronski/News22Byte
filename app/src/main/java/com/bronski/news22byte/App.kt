package com.bronski.news22byte

import android.app.Application
import com.bronski.news22byte.di.appModule
import com.bronski.news22byte.di.repoModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            modules(listOf(appModule, repoModule))
        }
    }
}