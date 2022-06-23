package cz.mendelu.project_xdivis1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.project_xdivis1.database.ILocalSessionsRepository
import cz.mendelu.project_xdivis1.model.Notes

class NotesListViewModel(private val repository: ILocalSessionsRepository) : ViewModel() {

    fun getPlanAll(): LiveData<MutableList<Notes>> {
        return repository.getPlanAll()
    }
}