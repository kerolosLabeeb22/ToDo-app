package com.example.todoapp

import android.app.Application
import com.example.todoapp.database.TasksDatabase

class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TasksDatabase.init(this)
    }
}