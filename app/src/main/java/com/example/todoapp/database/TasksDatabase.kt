package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.database.dao.TasksDao
import com.example.todoapp.database.model.Task

@Database(entities = arrayOf(Task::class), version = 2)
@TypeConverters(Converters::class)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun getTasksDao() : TasksDao

    companion object{
        private var DATABASE_INSTANCE : TasksDatabase? =null

        fun init(applicationContext : Context){
            if (DATABASE_INSTANCE == null){
                DATABASE_INSTANCE = Room.databaseBuilder(applicationContext,
                    TasksDatabase::class.java,
                    "Tasks Database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
        fun getInstance(): TasksDatabase{

            return DATABASE_INSTANCE!!
        }

    }
}