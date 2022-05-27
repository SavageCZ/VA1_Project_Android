package cz.mendelu.project_xdivis1

import android.app.Application
import cz.mendelu.project_xdivis1.di.daoModule
import cz.mendelu.project_xdivis1.di.databaseModule
import cz.mendelu.project_xdivis1.di.repositoryModule
import cz.mendelu.project_xdivis1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SessionsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                databaseModule,
                daoModule,
                repositoryModule,
                viewModelModule)
        }

    }
}