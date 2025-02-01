package com.example.todoapp.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.fragments.AddTaskFragment
import com.example.todoapp.fragments.SettingFragment
import com.example.todoapp.fragments.TasksFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TasksDatabase.getInstance().getTasksDao().getAllTasks()

        binding.btmNavView.setOnItemSelectedListener { menuItem->
            if(menuItem.itemId==R.id.navigation_task){
                showFragment(TasksFragment())
            }
            else if(menuItem.itemId==R.id.navigation_setting){
                showFragment(SettingFragment())
            }

            return@setOnItemSelectedListener true
        }

        binding.fabAddBtn.setOnClickListener {
            val bottomSheet = AddTaskFragment()
            bottomSheet.show(supportFragmentManager, null)
        }

        binding.btmNavView.selectedItemId=R.id.navigation_task

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }
}