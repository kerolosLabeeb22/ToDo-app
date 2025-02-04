package com.example.todoapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.adapter.TasksAdapter
import com.example.todoapp.clearTime
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.model.Task
import com.example.todoapp.databinding.FragmentTaskListBinding
import com.example.todoapp.setDate
import com.example.todoapp.view_container.CustomWeekDayBinder
import com.kizitonwose.calendar.core.atStartOfMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Date

class TaskListFragment : Fragment() {
    private lateinit var binding: FragmentTaskListBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var weekDayBinder: CustomWeekDayBinder
    private lateinit var calendar: Calendar
    private lateinit var taskDao: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()


        taskDao = TasksDatabase.getInstance().getTaskDoa()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            weekDayBinder = CustomWeekDayBinder(
                selectedTextColor = resources.getColor(R.color.blue, null),
                unselectedTextColor = resources.getColor(R.color.black, null)
            ) { weekDay ->
                val currentSelection = weekDayBinder.selectedDate
                if (currentSelection == weekDay.date) {
                    weekDayBinder.selectedDate = null
                    getTasksFromDataBase()
                    binding.weekCalendarView.notifyDateChanged(currentSelection)
                } else {
                    weekDayBinder.selectedDate = weekDay.date
                    calendar.setDate(
                        weekDay.date.year,
                        weekDay.date.monthValue - 1,
                        weekDay.date.dayOfMonth
                    )
                    calendar.clearTime()
                    getTaskByDateFromDataBase(calendar.time)
                    binding.weekCalendarView.notifyDateChanged(weekDay.date)
                    if (currentSelection != null) {
                        binding.weekCalendarView.notifyDateChanged(currentSelection)
                    }
                }
            }
            binding.weekCalendarView.dayBinder = weekDayBinder
            val currentDate = LocalDate.now()
            val currentMonth = YearMonth.now()
            val startDate = LocalDate.now()
            val endDate = currentMonth.plusMonths(100).atEndOfMonth()
            val firstDayOfWeek = DayOfWeek.SATURDAY
            binding.weekCalendarView.setup(startDate, endDate, firstDayOfWeek)
            binding.weekCalendarView.scrollToWeek(currentDate)
        }


        adapter = TasksAdapter(
            taskList = mutableListOf(),
            taskDao = taskDao
        )
        binding.recyclerViewTasks.adapter = adapter
        getTasksFromDataBase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTaskByDateFromDataBase(date: Date) {
        Log.e("Date", "Parameter = ${date.time}")
        val list = taskDao.getTasksByDate(date)
        adapter.taskList = list.toMutableList()
        adapter.notifyDataSetChanged()
    }

    fun getTasksFromDataBase() {
        if (isHidden) return
        val tasksList = taskDao.getAllTask()
        adapter.taskList = tasksList.toMutableList()
        adapter.notifyDataSetChanged()
    }
}
