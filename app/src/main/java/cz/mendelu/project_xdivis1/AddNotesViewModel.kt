package cz.mendelu.project_xdivis1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.project_xdivis1.database.ILocalSessionsRepository
import cz.mendelu.project_xdivis1.model.Notes
import cz.mendelu.project_xdivis1.model.UsedExercise


class AddNotesViewModel(private val repository: ILocalSessionsRepository) : ViewModel() {
    var id: Long? = null
    var plan: Notes = Notes("")

    suspend fun savePlan(){
        if(id == null){
            repository.insertPlan(plan)
        }else{
            repository.updatePlan(plan)
        }
    }
    suspend fun findPlanById(): Notes = repository.findPlanById(id!!)
    /*
        suspend fun getPlanWithUsedExercise(): LiveData<MutableList<PlanWithUsedExercise>> =
        repository.getPlanWithUsedExercise(plan.planName)

    */
    fun getUsedExercise(): LiveData<MutableList<UsedExercise>>{
        return repository.getUsedExercise(id!!)
    }


}
