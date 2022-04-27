package studio.iskaldvind.demohealth

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import studio.iskaldvind.demohealth.di.application
import studio.iskaldvind.demohealth.di.main

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, main))
        }
    }
}