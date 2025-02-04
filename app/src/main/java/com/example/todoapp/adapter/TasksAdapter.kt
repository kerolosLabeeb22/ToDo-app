package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.ItemTaskBinding
import com.zerobranch.layout.SwipeLayout

class TasksAdapter(
    var taskList: MutableList<Task>? = null,
    private val taskDao: TaskDao
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = taskList?.get(position) ?: return
        holder.bind(item, position)
    }

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, position: Int) {
            binding.title.text = task.title
            binding.time.text = "${task.date}"

            binding.swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if (direction == SwipeLayout.RIGHT) {
                        binding.leftView.visibility = View.VISIBLE
                        deleteTask(position)
                    }
                }

                override fun onClose() {

                }
            })
        }
    }

    private fun deleteTask(position: Int) {

        if (position < 0 || position >= taskList?.size ?: 0) return

        val task = taskList?.get(position) ?: return
        taskDao.deleteTask(task)
        taskList?.removeAt(position)
        notifyItemRemoved(position)


        if (taskList?.isEmpty() == true) {
            notifyDataSetChanged()
        }
    }
}
