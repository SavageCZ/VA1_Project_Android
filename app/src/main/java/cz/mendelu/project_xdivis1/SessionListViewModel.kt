package cz.mendelu.project_xdivis1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.project_xdivis1.database.ILocalSessionsRepository
import cz.mendelu.project_xdivis1.model.Session

class SessionListViewModel(private val repository: ILocalSessionsRepository) : ViewModel() {

    fun getAll(): LiveData<MutableList<Session>> {
        return repository.getAll()
    }


}