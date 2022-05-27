package cz.mendelu.project_xdivis1

import androidx.lifecycle.ViewModel
import cz.mendelu.project_xdivis1.database.ILocalSessionsRepository
import cz.mendelu.project_xdivis1.model.Session

class AddSessionViewModel(private val repository:ILocalSessionsRepository) : ViewModel() {

    var id: Long? = null
    var session: Session = Session("")

   suspend fun saveSession(){
        if(id == null){
            repository.insertTask(session)
        }else{
            repository.updateTask(session)
        }
    }

    suspend fun findTaskById():Session = repository.findById(id!!)

}