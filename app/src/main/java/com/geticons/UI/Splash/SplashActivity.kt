package com.geticons.UI.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.geticons.R
import com.geticons.UI.Home.Activities.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(object :Runnable{
            override fun run() {
               val intent=Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
            }

        },3000)
    }
}