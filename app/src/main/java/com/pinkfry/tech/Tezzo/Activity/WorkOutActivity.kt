package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkfry.tech.Tezzo.Adapter.DietTimeAdapter
import com.pinkfry.tech.Tezzo.Adapter.WorkoutDayAdapter
import com.pinkfry.tech.Tezzo.Model.*
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_diet_plan.*
import kotlinx.android.synthetic.main.activity_diet_plan.gifView
import kotlinx.android.synthetic.main.activity_diet_plan.tvPlanName
import kotlinx.android.synthetic.main.activity_work_out.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorkOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)
        val decor: View = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = resources.getColor(R.color.backgroundColor)

        var sharedPreferences = getSharedPreferences(
            resources.getString(R.string.packageName),
            Context.MODE_PRIVATE
        );
        val memberId = sharedPreferences.getString("member_id", "")!!
        getWorkOutResponse(memberId)
    }

    fun getWorkOutResponse(member_id: String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/workout/").build()
        retrofit.create(ApiCalls::class.java).getWorkOut(member_id).enqueue(object :
            Callback<WorkOutModel> {
            override fun onFailure(call: Call<WorkOutModel>, t: Throwable) {
                Toast.makeText(this@WorkOutActivity, "No Internet Screen", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<WorkOutModel>, response: Response<WorkOutModel>) {
                val dietResponse = response.body()
                if (dietResponse!!.isSuccess) {
                    if (dietResponse.msg.size != 0) {
                        val msgDiet = dietResponse.msg[0]
                        val singleDayWorkoutModel: ArrayList<SingleDayWorkoutModel> =
                            msgDiet.workout as ArrayList<SingleDayWorkoutModel>
                        tvPlanName.text = msgDiet.planName
                        rvDayBased.layoutManager = LinearLayoutManager(this@WorkOutActivity)
                        gifView.visibility = View.GONE
                        rvDayBased.adapter =
                            WorkoutDayAdapter(singleDayWorkoutModel, this@WorkOutActivity)
                    } else {
                        gifView.visibility = View.GONE
                        Toast.makeText(
                            this@WorkOutActivity,
                            "No WorkOut Added yet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }
        })

    }

}
