package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.util.getFormattedTime
import java.util.Calendar


class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = mutableListOf<Task>()


    @SuppressLint("NotifyDataSetChanged")
    fun setTaskList(tasks: MutableList<Task>){
        taskList =tasks
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding : ItemTaskBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(task : Task){
            binding.title.text=task.title
            val calendar = Calendar.getInstance()
            calendar.timeInMillis=task.time
            val hr =calendar.get(Calendar.HOUR)
            val min =calendar.get(Calendar.MINUTE)

            binding.time.text = getFormattedTime(hr, min)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false))

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

}