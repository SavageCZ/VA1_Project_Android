package cz.mendelu.project_xdivis1.database

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import cz.mendelu.project_xdivis1.model.Notes
import cz.mendelu.project_xdivis1.model.Session
import cz.mendelu.project_xdivis1.model.UsedExercise

interface ILocalSessionsRepository {

    fun getAll(): LiveData<MutableList<Session>>
    suspend fun findById(id: Long): Session
    suspend fun insertTask(session: Session): Long
    suspend fun updateTask(session: Session)
    suspend fun deleteTask(session: Session)
    suspend fun updateTaskStatus(id: Long, state:Boolean)

    //
    fun getPlanAll(): LiveData<MutableList<Notes>>
    suspend fun findPlanById(id: Long): Notes
    suspend fun insertPlan(plan: Notes): Long
    suspend fun updatePlan(plan: Notes)
    suspend fun deletePlan(plan: Notes)

    //
    fun getUsedExercise(planId: Long): LiveData<MutableList<UsedExercise>>


}