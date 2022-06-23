package cz.mendelu.project_xdivis1.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.project_xdivis1.model.Notes
import cz.mendelu.project_xdivis1.model.Session
import cz.mendelu.project_xdivis1.model.UsedExercise

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

    //////

    @Query("SELECT * FROM notes")
    fun getPlanAll(): LiveData<MutableList<Notes>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun findPlanById(id: Long): Notes

    @Insert
    suspend fun insertPlan(notes: Notes): Long

    @Update
    suspend fun updatePlan(notes: Notes)

    @Delete
    suspend fun deletePlan(notes: Notes)

    ///try this to show exercises in plan fragment
    @Transaction
    @Query ("SELECT * FROM used_exercise WHERE fromPlanId = :planId")
    fun getUsedExercise(planId: Long): LiveData<MutableList<UsedExercise>>

}