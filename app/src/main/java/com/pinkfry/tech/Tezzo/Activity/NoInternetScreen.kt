package com.pinkfry.tech.Tezzo.Activity

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.activity_no_internet_screen.*


class NoInternetScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet_screen)
        val decor: View = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor=resources.getColor(R.color.colorNoInternetBackground)

        tvRetry.setOnClickListener {
                  if(isNetworkAvailable()) {
                      setResult(Activity.RESULT_OK)
                      finish()
                  }
                  else
                      Toast.makeText(this@NoInternetScreen,"No InternetConnection",Toast.LENGTH_SHORT).show()
        }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
