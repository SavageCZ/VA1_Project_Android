package cz.mendelu.project_xdivis1.database

import androidx.lifecycle.LiveData
import cz.mendelu.project_xdivis1.model.Session

class LocalSessionsRepositoryImpl(private val dao: SessionsDao) : ILocalSessionsRepository {


    override fun getAll(): LiveData<MutableList<Session>> {
        return dao.getAll()
    }

    override suspend fun findById(id: Long): Session {
        return dao.findById(id)
    }

    override suspend fun insertTask(session: Session): Long {
        return dao.insertTask(session)
    }

    override suspend fun updateTask(session: Session) {
        dao.updateTask(session)
    }

    override suspend fun deleteTask(session: Session) {
        dao.deleteTask(session)
    }

    override suspend fun updateTaskStatus(id: Long, state: Boolean)
       = dao.updateTaskStatus(id, state)
}