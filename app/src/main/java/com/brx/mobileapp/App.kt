package com.brx.mobileapp

import android.app.Application
import com.brx.mobileapp.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                Modules.remotes,
                Modules.useCases,
                Modules.repositories,
                Modules.viewModels
            )
        }
    }
}