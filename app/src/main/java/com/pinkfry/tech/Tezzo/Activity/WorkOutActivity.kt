package com.pinkfry.tech.Tezzo.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Adapter.AdapterParticularWorkoutPlan
import com.pinkfry.tech.Tezzo.Adapter.AdapterShowWorkoutPlans
import com.pinkfry.tech.Tezzo.Adapter.DietTimeAdapter
import com.pinkfry.tech.Tezzo.Adapter.WorkoutDayAdapter
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.*
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_diet_plan.*
import kotlinx.android.synthetic.main.activity_diet_plan.gifView
import kotlinx.android.synthetic.main.activity_work_out.*
import kotlinx.android.synthetic.main.no_internet_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorkOutActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var customDialogeProgressBar: CustomDialogeProgressBar
    lateinit var adapterShowWorkoutPlan: AdapterShowWorkoutPlans
    var allWorkoutNameArraylist = arrayListOf<String>()

    companion object {
        lateinit var mutableScroll: MutableLiveData<Int>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)
        val decor: View = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = resources.getColor(R.color.backgroundColor)
        customDialogeProgressBar = CustomDialogeProgressBar(this)
        mutableScroll = MutableLiveData()
        rvSingleWorkoutPlan.layoutManager = LinearLayoutManager(this@WorkOutActivity)
        rvAllWorkoutPlans.layoutManager =
            LinearLayoutManager(this@WorkOutActivity, LinearLayoutManager.HORIZONTAL, false)
        adapterShowWorkoutPlan = AdapterShowWorkoutPlans(allWorkoutNameArraylist, 0, this)
        rvAllWorkoutPlans.adapter = adapterShowWorkoutPlan

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

        mutableScroll.observeForever(object : Observer<Int> {
            override fun onChanged(t: Int?) {
                if (t != null)
                    rvSingleWorkoutPlan.scrollToPosition(t)
            }
        })

    }

    fun getWorkOutResponse(member_id: String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/workout/").build()
        retrofit.create(ApiCalls::class.java).getWorkOut(member_id).enqueue(object :
            Callback<WorkOutModel> {
            override fun onFailure(call: Call<WorkOutModel>, t: Throwable) {
//                val intent= Intent(this@WorkOutActivity,NoInternetScreen::class.java)
//                startActivityForResult(intent,2415);
                linearWholeWorkout.visibility = View.INVISIBLE;
                gifView.visibility = View.INVISIBLE
                linearNoInternet.visibility = View.VISIBLE
                customDialogeProgressBar.dismiss()
            }

            override fun onResponse(call: Call<WorkOutModel>, response: Response<WorkOutModel>) {
                customDialogeProgressBar.dismiss()
                val dietResponse = response.body()
                linearWholeWorkout.visibility = View.VISIBLE;
                gifView.visibility = View.VISIBLE
                linearNoInternet.visibility = View.INVISIBLE
                if (dietResponse!!.isSuccess) {
                    if (dietResponse.msg.size != 0) {
                        val arrayListOfPlans = ArrayList<MsgWorkOut>()
                        for (element in dietResponse.msg) {
                            allWorkoutNameArraylist.add(element.planName)
                            arrayListOfPlans.add(element)
                        }
//                        val msgDiet = dietResponse.msg[0]
//                        val singleDayWorkoutModel: ArrayList<SingleDayWorkoutModel> =
//                            msgDiet.workout as ArrayList<SingleDayWorkoutModel>
//                        tvPlanName.text = msgDiet.planName
                        adapterShowWorkoutPlan.notifyDataSetChanged()
                        gifView.visibility = View.GONE
                        val adapterParticularWorkoutPlan =
                        AdapterParticularWorkoutPlan(arrayListOfPlans, this@WorkOutActivity)
                        rvSingleWorkoutPlan.adapter = adapterParticularWorkoutPlan
//                        rvSingleWorkoutPlan.smoothScrollToPosition(1)

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
