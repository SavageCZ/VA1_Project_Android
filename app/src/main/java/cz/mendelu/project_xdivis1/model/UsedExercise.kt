package cz.mendelu.project_xdivis1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "used_exercise")
data class UsedExercise(
    @ColumnInfo(name = "usedExerciseName")
    var usedExerciseName: String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "fromPlanId")
    var fromPlanId: Long? = null

    @ColumnInfo(name = "reps")
    var reps: Int? = null
    @ColumnInfo(name = "sets")
    var sets: Int? = null
    @ColumnInfo(name = "pause")
    var pause: Long? = null

    @ColumnInfo(name = "planName")
    var planName: String? = null
}