package com.example.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.model.Task
import java.util.Date
@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Query("select * from tasks")
    fun getAllTask():List<Task>
    @Update
    fun update(task: Task)
    @Query("select * from tasks where date = :selectedDate")
    fun getTasksByDate(selectedDate: Date):List<Task>

    @Query("select * from tasks where id = :taskId")
    fun getTaskById(taskId:Int):Task

}