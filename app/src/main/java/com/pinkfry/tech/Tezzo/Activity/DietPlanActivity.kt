package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pinkfry.tech.Tezzo.Model.DietResponse
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DietPlanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)

        var sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE);
        val memberId=sharedPreferences.getString("member_id","")!!
        getDietResponse(memberId )

    }

    fun getDietResponse(member_id:String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/diet/").build()
        retrofit.create(ApiCalls::class.java).getDietData(member_id).enqueue(object: Callback<DietResponse>{
            override fun onFailure(call: Call<DietResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<DietResponse>, response: Response<DietResponse>) {
                val dietResponse=response.body()
                Log.d("DPA",dietResponse.toString())

            }
        })

    }
}
