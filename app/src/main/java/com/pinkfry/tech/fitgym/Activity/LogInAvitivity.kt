package com.pinkfry.tech.fitgym.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pinkfry.tech.fitgym.R

class LogInAvitivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor=resources.getColor(R.color.colorPrimary)
        setContentView(R.layout.activity_log_in_avitivity)
    }
}
