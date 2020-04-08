package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pinkfry.tech.Tezzo.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash_screen_)
        var handler= Handler();
        handler.postDelayed({ },2000)
        val sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE)
        val isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false)
        if(isLoggedIn){
            val intent=Intent(this@SplashScreenActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val intent=Intent(this@SplashScreenActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
