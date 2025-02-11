package com.example.todoapp.database.design_patterns

object AppConstants {
    const val TASK_ID = "task_id"
}
class Database private constructor(){
    companion object {
        private var instance : Database? = null
        fun getInstance():Database {
            if(instance == null){
                instance = Database()
            }
            return instance!!
        }
    }


}