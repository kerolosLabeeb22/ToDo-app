package com.example.todoapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0, //
    val title: String? = null,
    val date: Date? = null,
    var isDone: Boolean? = false,
) {
    @Ignore
    var description: String? = null
}
val task = Task(title = "Play Basketball", date = Date(), isDone = true)



