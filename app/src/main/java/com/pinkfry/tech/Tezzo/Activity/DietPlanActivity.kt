package com.pinkfry.tech.Tezzo.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.pinkfry.tech.Tezzo.Adapter.DietTimeAdapter
import com.pinkfry.tech.Tezzo.Model.DietItem
import com.pinkfry.tech.Tezzo.Model.DietResponse
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_diet_plan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DietPlanActivity : AppCompatActivity() {
 lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)
        val decor: View =window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor=resources.getColor(R.color.backgroundColor)

         sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE);
        val memberId=sharedPreferences.getString("member_id","")!!
        getDietResponse(memberId )





    }

    fun getDietResponse(member_id:String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/diet/").build()
        retrofit.create(ApiCalls::class.java).getDietData(member_id).enqueue(object: Callback<DietResponse>{
            override fun onFailure(call: Call<DietResponse>, t: Throwable) {
                val intent=Intent(this@DietPlanActivity,NoInternetScreen::class.java)
                startActivityForResult(intent,416)
            }

            override fun onResponse(call: Call<DietResponse>, response: Response<DietResponse>) {
                val dietResponse=response.body()
                if(dietResponse!!.isSuccess) {
                    val msgDiet = dietResponse.msg[0]
                    val dietItem:ArrayList<DietItem> = msgDiet.diet as ArrayList<DietItem>
                    tvPlanName.text=msgDiet.dietName
                    rvTimeBased.layoutManager=LinearLayoutManager(this@DietPlanActivity)
                    gifView.visibility=View.GONE
                    rvTimeBased.adapter=DietTimeAdapter(dietItem,this@DietPlanActivity)
                }


            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode==416)  and (resultCode==Activity.RESULT_OK )){
            val memberId=sharedPreferences.getString("member_id","")!!
            getDietResponse(memberId)
        }
        else{

        }
    }


}
