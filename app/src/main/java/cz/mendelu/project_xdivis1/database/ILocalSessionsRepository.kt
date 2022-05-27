package cz.mendelu.project_xdivis1.database

import androidx.lifecycle.LiveData
import cz.mendelu.project_xdivis1.model.Session

interface ILocalSessionsRepository {

    fun getAll(): LiveData<MutableList<Session>>
    suspend fun findById(id: Long): Session
    suspend fun insertTask(session: Session): Long
    suspend fun updateTask(session: Session)
    suspend fun deleteTask(session: Session)
    suspend fun updateTaskStatus(id: Long, state:Boolean)

}