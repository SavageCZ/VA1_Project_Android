package cz.mendelu.project_xdivis1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Notes(
    @ColumnInfo(name = "noteName")
    var noteName: String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

}