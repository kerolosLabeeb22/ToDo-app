package com.example.todoapp.view_container

import android.os.Build
import android.view.View

import com.example.todoapp.databinding.ItemWeekDayBinding
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


    //                       (Inputs) -> Output
    class CustomWeekDayBinder(
        val selectedTextColor: Int,
        val unselectedTextColor: Int,
        val onDateSelected: (WeekDay) -> Unit
    ) : WeekDayBinder<DayViewContainer> {
        var selectedDate: LocalDate? = null
        override fun bind(container: DayViewContainer, data: WeekDay) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                container.weekDayNameText.text = data.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault()
                )
                container.monthDayNameText.text = "${data.date.dayOfMonth}"
                if (selectedDate == data.date) {
                    container.weekDayNameText.setTextColor(selectedTextColor)
                    container.monthDayNameText.setTextColor(selectedTextColor)
                } else {
                    container.weekDayNameText.setTextColor(unselectedTextColor)
                    container.monthDayNameText.setTextColor(unselectedTextColor)
                }
                container.view.setOnClickListener {
                    onDateSelected(data)
                }
            }
        }

        override fun create(view: View): DayViewContainer {
            return DayViewContainer(view)
        }


    }

    class DayViewContainer(view: View) : ViewContainer(view) {
        val weekDayNameText = ItemWeekDayBinding.bind(view).weekDayNameTextView
        val monthDayNameText = ItemWeekDayBinding.bind(view).monthDayTextView
    }