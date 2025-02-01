package com.example.todoapp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.R
import com.example.todoapp.activity.HomeActivity
import java.util.logging.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        android.os.Handler(Looper.getMainLooper())
            .postDelayed({
                navigateToHomeScreen()
            }, 2000)
    }
    private fun navigateToHomeScreen(){
        intent= Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }
}