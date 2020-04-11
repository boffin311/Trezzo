package com.pinkfry.tech.Tezzo.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.pinkfry.tech.Tezzo.Activity.DietPlanActivity
import com.pinkfry.tech.Tezzo.Adapter.NoticeAdapter
import com.pinkfry.tech.Tezzo.Model.AttendanceModel
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.alert_dialogue_attendance.view.*
import kotlinx.android.synthetic.main.content_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FragmentMainScreen: Fragment() {
    lateinit var alertDialog: AlertDialog;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInAlertDialogue()
    }

//    override fun onStart() {
//        super.onStart()
//        if (alertDialog.window!=null) {
//            Toast.makeText(context, "isNotNUll " , Toast.LENGTH_LONG).show();
//            alertDialog.window!!.attributes.windowAnimations = R.style.SlidingDialogAnimation
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.content_main,container,false)

           view.cardDiet.setOnClickListener {
               var intent=Intent(activity,DietPlanActivity::class.java)
               startActivity(intent)
           }
        view.cardAttendance.setOnClickListener {
            val intentInitiator=IntentIntegrator.forSupportFragment(this)
                intentInitiator.initiateScan()

        }
       view.rvNotice.layoutManager=LinearLayoutManager(context)
        var arrayList= arrayListOf<String>("Important Notice regarding suspension of classes  Download","Admission Notice 2020-2021 Download","MBA Admission 2020-2021 Download","Date sheet for mid term examination March 2020(Reappear) Download");
        view.rvNotice.adapter=NoticeAdapter(arrayList);
        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result != null) {
            if(result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(context, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
                var sharedPreferences=activity!!.getSharedPreferences(resources.getString(R.string.packageName),Context.MODE_PRIVATE)
                getAttendanceResponse(sharedPreferences.getString("member_id","")!!,result.contents)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    fun getAttendanceResponse(member_id:String,gymId:String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/attendance/").build()
        retrofit.create(ApiCalls::class.java).getAttendanceResult(member_id,gymId).enqueue(object:
            Callback<AttendanceModel> {
            override fun onFailure(call: Call<AttendanceModel>, t: Throwable) {
                Toast.makeText(context, "failed " , Toast.LENGTH_LONG).show();
            }

            override fun onResponse(call: Call<AttendanceModel>, response: Response<AttendanceModel>) {
                val attendanceModel=response.body()
                if(attendanceModel!!.isSuccess) {
                    val msgDiet = attendanceModel.msg
                    alertDialog.show()

                }
                else{
                    Toast.makeText(context, "false " , Toast.LENGTH_LONG).show();
                }


            }
        })

    }
    fun checkInAlertDialogue(){
        val layoutInflater=layoutInflater
        val view=layoutInflater.inflate(R.layout.alert_dialogue_attendance,null,false);

        view.animation = AnimationUtils.loadAnimation(context,R.anim.dual_scale)
        alertDialog=AlertDialog.Builder(context).create()
        alertDialog.setView(view)


    }
}