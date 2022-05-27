package cz.mendelu.project_xdivis1.di

import cz.mendelu.project_xdivis1.database.ILocalSessionsRepository
import cz.mendelu.project_xdivis1.database.LocalSessionsRepositoryImpl
import cz.mendelu.project_xdivis1.database.SessionsDao
import org.koin.dsl.module

val repositoryModule = module {
    single {
        provideLocalTasksRepository(get())
    }
}

fun provideLocalTasksRepository(dao: SessionsDao):
        ILocalSessionsRepository = LocalSessionsRepositoryImpl(dao)