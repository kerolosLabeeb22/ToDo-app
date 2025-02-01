package com.example.todoapp.database.design_patterns

object AppConstants{

}

val appConstants1 = AppConstants
val appConstants2 = AppConstants

class Database private constructor(){
    companion object{
        private var instance : Database? = null
        fun getInstance(): Database{
            if (instance == null){
                instance = Database()
            }
            return instance!!
        }
    }

}

val database1= Database.getInstance()
val database2 = Database.getInstance()