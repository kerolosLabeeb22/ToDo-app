package com.example.todoapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.R
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.database.dao.TasksDao
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.util.clearTime
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar


class TasksFragment : Fragment() {


    lateinit var binding: FragmentTasksBinding
    private val adapter = TaskAdapter()
    private lateinit var dao: TasksDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = TasksDatabase.getInstance().getTasksDao()
        initRecyclerView()
        initCalendarView()
    }

    private fun initCalendarView() {
        binding.calendarView.selectedDate= CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { _, date, selected ->
            val calendar  = Calendar.getInstance()
            calendar.set(Calendar.YEAR,date.year)
            calendar.set(Calendar.MONTH,date.month-1)
            calendar.set(Calendar.DAY_OF_MONTH,date.day)
            calendar.clearTime()
            if (selected){
                val tasks=dao.getAllTasksByDate(calendar.timeInMillis).toMutableList()
                Log.e("TAG","initCalendarView:$tasks",)
                adapter.setTaskList(tasks)
            }

        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewTasks.adapter=adapter
    }

    override fun onStart() {
        super.onStart()
        loadAllTasksOfDate(getSelectedDate().timeInMillis)
    }

    private fun loadAllTasksOfDate(date: Long){
        val tasks =dao.getAllTasksByDate(date).toMutableList()
        adapter.setTaskList(tasks)
    }

    private fun getSelectedDate():Calendar{
       val calendar = Calendar.getInstance()
       if(binding.calendarView.selectedDate != null){
           calendar.set(Calendar.YEAR,binding.calendarView.selectedDate!!.year)
       }
        binding.calendarView.selectedDate?.let {date->
            calendar.set(Calendar.YEAR,date.year)
            calendar.set(Calendar.MONTH,date.month-1)
            calendar.set(Calendar.DAY_OF_MONTH,date.day)
        }
        calendar.clearTime()

        return calendar
    }


}