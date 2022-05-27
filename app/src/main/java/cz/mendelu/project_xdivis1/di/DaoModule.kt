package cz.mendelu.project_xdivis1.di

import cz.mendelu.project_xdivis1.database.SessionsDao
import cz.mendelu.project_xdivis1.database.SessionsDatabase
import org.koin.dsl.module

val daoModule = module {

    single {
        provideDao(get())
    }

}

fun provideDao(database: SessionsDatabase):
        SessionsDao = database.tasksDao()