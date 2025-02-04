package com.example.todoapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.callbacks.OnTaskAddedListener
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.fragments.AddTaskFragment
import com.example.todoapp.fragments.SettingsFragment
import com.example.todoapp.fragments.TaskListFragment


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var taskListFragment: TaskListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskListFragment = TaskListFragment()
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TasksDatabase.getInstance().getTaskDoa()

        binding.btmNavView.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.navigation_task_list->{
                    showFragment(taskListFragment)
                }
                R.id.navigation_setting->{
                    showFragment(SettingsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        binding.fabAddBtn.setOnClickListener {
            val addTaskFragment =
                AddTaskFragment()
            addTaskFragment.onTaskAddedListener = OnTaskAddedListener {
                // Logic
                // taskListFragment should Refresh Itself
                taskListFragment.getTasksFromDataBase()
            }
            addTaskFragment.show(supportFragmentManager, null)
        }
        binding.btmNavView.selectedItemId=R.id.navigation_task_list
    }
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }
}