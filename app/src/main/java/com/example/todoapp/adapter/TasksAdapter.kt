package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.ItemTaskBinding
import com.zerobranch.layout.SwipeLayout

class TasksAdapter(
    var taskList: MutableList<Task>? = null,
    private val taskDao: TaskDao
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var onClickListener: OnClickListener? = null

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

        @SuppressLint("ResourceAsColor")
        fun bind(task: Task, position: Int) {

            val context = binding.root.context
            val greenColor = ContextCompat.getColor(context, R.color.green)
            val blueColor = ContextCompat.getColor(context, R.color.secondary)
            binding.title.text = task.title
            binding.time.text = "${task.date}"

            if (task.isDone == true) {
                binding.title.setTextColor(greenColor)
                binding.doneText.setTextColor(greenColor)
                binding.dragImg.setBackgroundColor(greenColor)
                binding.checkBtn.visibility = View.GONE
                binding.doneText.visibility = View.VISIBLE
            } else {
                binding.title.setTextColor(blueColor)
                binding.dragImg.setBackgroundColor(blueColor)
                binding.checkBtn.visibility = View.VISIBLE
                binding.doneText.visibility = View.GONE
            }

            binding.checkBtn.setOnClickListener {
                task.isDone = true
                taskDao.update(task)

                binding.title.setTextColor(greenColor)
                binding.doneText.setTextColor(greenColor)
                binding.dragImg.setBackgroundColor(greenColor)
                binding.checkBtn.visibility = View.GONE
                binding.doneText.visibility = View.VISIBLE


            }

            binding.dragLayout.setOnClickListener {
                onClickListener?.onClick(position, task)
            }



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

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, task: Task)
    }

}
