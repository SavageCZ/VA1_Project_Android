package cz.mendelu.project_xdivis1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.project_xdivis1.database.ILocalSessionsRepository
import cz.mendelu.project_xdivis1.model.Session

class MapsViewModel(private val repository: ILocalSessionsRepository): ViewModel() {

    var latitude: Double? = null
    var longitude: Double? = null
    var locationChanged: Boolean = false


    fun getAll(): LiveData<MutableList<Session>> {
        return repository.getAll()
    }


}