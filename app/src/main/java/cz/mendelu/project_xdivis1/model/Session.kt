package cz.mendelu.project_xdivis1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sessions")
data class Session(@ColumnInfo(name = "text") var text: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "date")
    var date: Long? = null

    @ColumnInfo(name = "done")
    var done: Boolean = false

    @ColumnInfo(name = "latitude")
    var latitude: Double? = null

    @ColumnInfo(name = "longitude")
    var longitude: Double? = null

}