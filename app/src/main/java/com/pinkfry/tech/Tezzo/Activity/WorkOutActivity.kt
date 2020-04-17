package com.pinkfry.tech.Tezzo.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkfry.tech.Tezzo.Adapter.DietTimeAdapter
import com.pinkfry.tech.Tezzo.Adapter.WorkoutDayAdapter
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.*
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_diet_plan.*
import kotlinx.android.synthetic.main.activity_diet_plan.gifView
import kotlinx.android.synthetic.main.activity_diet_plan.tvPlanName
import kotlinx.android.synthetic.main.activity_work_out.*
import kotlinx.android.synthetic.main.no_internet_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorkOutActivity : AppCompatActivity() {
lateinit var sharedPreferences:SharedPreferences
    lateinit var customDialogeProgressBar: CustomDialogeProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)
        val decor: View = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = resources.getColor(R.color.backgroundColor)
        customDialogeProgressBar= CustomDialogeProgressBar(this)

         sharedPreferences = getSharedPreferences(
            resources.getString(R.string.packageName),
            Context.MODE_PRIVATE
        );
        val memberId = sharedPreferences.getString("member_id", "")!!
        getWorkOutResponse(memberId)
        tvRetry.setOnClickListener {
           customDialogeProgressBar.show()
            customDialogeProgressBar.setCancelable(false)
            getWorkOutResponse(memberId)

        }
    }

    fun getWorkOutResponse(member_id: String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/workout/").build()
        retrofit.create(ApiCalls::class.java).getWorkOut(member_id).enqueue(object :
            Callback<WorkOutModel> {
            override fun onFailure(call: Call<WorkOutModel>, t: Throwable) {
//                val intent= Intent(this@WorkOutActivity,NoInternetScreen::class.java)
//                startActivityForResult(intent,2415);
                linearWholeWorkout.visibility=View.INVISIBLE;
                gifView.visibility=View.INVISIBLE
                linearNoInternet.visibility=View.VISIBLE
                customDialogeProgressBar.dismiss()
            }

            override fun onResponse(call: Call<WorkOutModel>, response: Response<WorkOutModel>) {
                customDialogeProgressBar.dismiss()
                val dietResponse = response.body()
                linearWholeWorkout.visibility=View.VISIBLE;
                gifView.visibility=View.VISIBLE
                linearNoInternet.visibility=View.INVISIBLE
                if (dietResponse!!.isSuccess) {
                    if (dietResponse.msg.size != 0) {
                        val msgDiet = dietResponse.msg[0]
                        val singleDayWorkoutModel: ArrayList<SingleDayWorkoutModel> =
                            msgDiet.workout as ArrayList<SingleDayWorkoutModel>
                        tvPlanName.text = msgDiet.planName
                        rvDayBased.layoutManager = LinearLayoutManager(this@WorkOutActivity)
                        gifView.visibility = View.GONE
                        rvDayBased.adapter = WorkoutDayAdapter(singleDayWorkoutModel, this@WorkOutActivity)

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
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if ((requestCode==2415)  and (resultCode== Activity.RESULT_OK )){
//            val memberId=sharedPreferences.getString("member_id","")!!
//            getWorkOutResponse(memberId)
//        }
//
//    }

}
