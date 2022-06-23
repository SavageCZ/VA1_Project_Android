package cz.mendelu.project_xdivis1.di

import cz.mendelu.project_xdivis1.AddSessionViewModel
import cz.mendelu.project_xdivis1.MapsViewModel
import cz.mendelu.project_xdivis1.SessionListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SessionListViewModel(get())
    }

    viewModel {
        AddSessionViewModel(get())
    }

    viewModel{
        MapsViewModel(get()) // nemůžeme uložit do databáze, proto bez get()
    }

}