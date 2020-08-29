package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkfry.tech.Tezzo.Adapter.*
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.DietModel.DietResponse
import com.pinkfry.tech.Tezzo.Model.DietModel.MsgDiet
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_diet_plan.*
import kotlinx.android.synthetic.main.activity_diet_plan.gifView
import kotlinx.android.synthetic.main.no_internet_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DietPlanActivity : AppCompatActivity() {
 lateinit var sharedPreferences: SharedPreferences
    lateinit var customDialogeProgressBar: CustomDialogeProgressBar
    lateinit var adapterShowDietPlan: AdapterShowDietPlan
    var allDietNameArrayList = arrayListOf<String>()

    companion object {
        lateinit var mutableScroll: MutableLiveData<Int>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)
        val decor: View =window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor=resources.getColor(R.color.backgroundColor)
        customDialogeProgressBar= CustomDialogeProgressBar(this)
        mutableScroll = MutableLiveData()
        rvSingleDietPlan.layoutManager = LinearLayoutManager(this@DietPlanActivity)
        rvAllDietPlans.layoutManager=LinearLayoutManager(this@DietPlanActivity,LinearLayoutManager.HORIZONTAL,false)
        adapterShowDietPlan = AdapterShowDietPlan(allDietNameArrayList, 0, this)
        rvAllDietPlans.adapter = adapterShowDietPlan
         sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE);
        val memberId=sharedPreferences.getString("member_id","")!!
        getDietResponse(memberId )

        tvRetry.setOnClickListener {
            customDialogeProgressBar.show()
            customDialogeProgressBar.setCancelable(false)
            getDietResponse(memberId)

        }

        mutableScroll.observeForever { t ->
            if (t != null)
                rvSingleDietPlan.scrollToPosition(t)
        }

    }





    fun getDietResponse(member_id:String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.tezzo.fit/diet/").build()
            .baseUrl(resources.getString(R.string.baseUrlServer,"diet/")).build()
        retrofit.create(ApiCalls::class.java).getDietData(member_id).enqueue(object: Callback<DietResponse>{
            override fun onFailure(call: Call<DietResponse>, t: Throwable) {
//                val intent=Intent(this@DietPlanActivity,NoInternetScreen::class.java)
//                startActivityForResult(intent,416)

                linearWholeDiet.visibility=View.INVISIBLE;
                gifView.visibility=View.INVISIBLE
                linearNoInternet.visibility=View.VISIBLE
                customDialogeProgressBar.dismiss()
            }

            override fun onResponse(call: Call<DietResponse>, response: Response<DietResponse>) {
                customDialogeProgressBar.dismiss()
                val dietResponse=response.body()
                linearWholeDiet.visibility=View.VISIBLE;
                gifView.visibility=View.VISIBLE
                linearNoInternet.visibility=View.INVISIBLE
                if(dietResponse!!.isSuccess) {
                    if (dietResponse.msg.size != 0) {
                        val arrayListOfPlans = ArrayList<MsgDiet>()
                        for (element in dietResponse.msg) {
                        allDietNameArrayList.add(element.dietName)
                            arrayListOfPlans.add(element)
                        }

                   adapterShowDietPlan.notifyDataSetChanged()
                    gifView.visibility=View.GONE
                    val adapterParticularDietPlan= AdapterParticularDietPlan(arrayListOfPlans, this@DietPlanActivity)
                    rvSingleDietPlan.adapter=adapterParticularDietPlan
                     }
                }
                else {
                    gifView.visibility = View.GONE
                    Toast.makeText(
                        this@DietPlanActivity,
                        "No Diet Added yet",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
        })

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if ((requestCode==416)  and (resultCode==Activity.RESULT_OK )){
//            val memberId=sharedPreferences.getString("member_id","")!!
//            getDietResponse(memberId)
//        }
//        else{
//
//        }
//    }


}
