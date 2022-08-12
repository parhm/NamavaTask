package ta.parham.namavatask

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ta.parham.namavatask.di.networkModule
import ta.parham.namavatask.di.repositoryModule
import ta.parham.namavatask.di.storageModule
import ta.parham.namavatask.di.viewModelModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialise logcat library
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(networkModule, storageModule, repositoryModule, viewModelModule))
        }
    }
}