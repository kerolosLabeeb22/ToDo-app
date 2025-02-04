package com.example.todoapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.callbacks.OnTaskAddedListener
import com.example.todoapp.clearTime
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.databinding.FragmentTaskListBinding
import com.example.todoapp.setDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date

class AddTaskFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var calendar: Calendar
    var onTaskAddedListener: OnTaskAddedListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendar = Calendar.getInstance()
        binding.selectDateTv.setOnClickListener {
            showDate()
        }
        super.onViewCreated(view, savedInstanceState)
        binding.addTaskBtn.setOnClickListener {
            if (validateInputs())
                addTask()
        }
    }

    fun showDate() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                calendar.setDate(year, month, dayOfMonth)
                calendar.clearTime()
                binding.selectDateTv.text = "${dayOfMonth}/${month + 1}/${year}"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = Date().time
        datePicker.show()
    }


    private fun validateInputs(): Boolean {
        var isValid = true
        if (binding.title.text.isNullOrEmpty() || binding.title.text.isNullOrBlank()) {
            binding.title.error = "Title is required"
        } else {
            binding.title.error = null
        }
        return isValid
    }

    fun addTask() {
        TasksDatabase.getInstance().getTaskDoa().insertTask(Task(0,binding.title.text.toString(),calendar.time))
        onTaskAddedListener?.onTaskAdded()
        dismiss()
    }
}