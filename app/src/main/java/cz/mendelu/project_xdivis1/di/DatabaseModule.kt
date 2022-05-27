package cz.mendelu.project_xdivis1.di

import android.content.Context
import cz.mendelu.project_xdivis1.database.SessionsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }

}

fun provideDatabase(context: Context): SessionsDatabase = SessionsDatabase.getDatabase(context)