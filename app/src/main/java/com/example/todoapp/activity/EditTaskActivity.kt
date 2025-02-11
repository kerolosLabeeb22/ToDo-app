package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.design_patterns.AppConstants
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.ActivityEditTaskBinding

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    private lateinit var taskDao: TaskDao
    private var taskId: Int =-1
    private var task:Task?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskDao= TasksDatabase.getInstance().getTaskDoa()

        taskId=intent.getIntExtra(AppConstants.TASK_ID,-1)
        if (taskId !=-1){
            task=taskDao.getTaskById(taskId)
            task?.let { fillTaskData(it) }
        }
        binding.saveBtn.setOnClickListener {
            saveTaskChanges()
        }
    }

    private fun saveTaskChanges() {
        val newTitle=binding.title.text.toString().trim()
        val newDescription=binding.description.text.toString().trim()

        if (newTitle.isBlank() || newDescription.isBlank()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }

        task?.let {
            it.title =newTitle
            it.description=newDescription
            taskDao.update(it)

            val resultIntent = Intent()
            resultIntent.putExtra(AppConstants.TASK_ID, taskId)
            setResult(RESULT_OK, resultIntent)


            Toast.makeText(this,"Task updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fillTaskData(task: Task) {
        binding.title.setText(task.title)
        binding.description.setText(task.description)
        binding.selectDateTv.text=task.date.toString()


    }
}