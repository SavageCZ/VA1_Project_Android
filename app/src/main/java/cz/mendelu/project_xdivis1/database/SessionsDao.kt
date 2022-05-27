package cz.mendelu.project_xdivis1.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.project_xdivis1.model.Session

@Dao
interface SessionsDao {

    @Query("SELECT * FROM sessions")
    fun getAll(): LiveData<MutableList<Session>>

    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun findById(id: Long): Session

    @Insert
    suspend fun insertTask(session: Session): Long

    @Update
    suspend fun updateTask(session: Session)

    @Delete
    suspend fun deleteTask(session: Session)

    @Query("UPDATE sessions SET done = :state WHERE id = :id")
    suspend fun updateTaskStatus(id: Long, state:Boolean)

}