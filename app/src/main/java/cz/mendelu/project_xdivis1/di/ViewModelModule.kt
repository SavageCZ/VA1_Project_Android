package cz.mendelu.project_xdivis1.di

import cz.mendelu.project_xdivis1.*
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

    viewModel{
        NotesListViewModel(get())
    }

    viewModel{
        AddNotesViewModel(get())
    }

}