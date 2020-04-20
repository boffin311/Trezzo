package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import com.pinkfry.tech.Tezzo.Adapter.AttendanceAdapter
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.AttendanceModel
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_attendance_show.*
import kotlinx.android.synthetic.main.dialogue_choose_month.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class AttendanceShowActivity : AppCompatActivity(){
    lateinit var arrayList:ArrayList<Int>
    lateinit var customDialogProgressBar:CustomDialogeProgressBar
     var arrayAttendance=Array<Int>(31){0}
    private val monthArray= arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
    private val yearArray= arrayOf("2020","2021","2022","2023","2024","2025","2026")
    private lateinit var  alertDialog: AlertDialog.Builder
    lateinit var attendanceDoneAlertDialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_show)
        val decor: View =window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor=getColor(R.color.backgroundColor)


        //Custom Dialogue for showing progressbar and checkInAlertDialog box
        checkInAlertDialogue()
        customDialogProgressBar= CustomDialogeProgressBar(this)
        customDialogProgressBar.setCancelable(false)
        customDialogProgressBar.window!!.setBackgroundDrawableResource(android.R.color.transparent);

        //to find the current month and year
        arrayList= ArrayList()
        val calendar=Calendar.getInstance()
        val month=calendar.get(Calendar.MONTH)
        val year=calendar.get(Calendar.YEAR)
        val firstDayOfMonth=getFirstDayOfCurrentMonth(month,year)

        //creating arrayList of all the days of the month
        attachCalendarView(firstDayOfMonth,calendar,month,year)
        rvAttendance.layoutManager=GridLayoutManager(this,7)

        //as name suggests
        getDataForTheAttendanceAndShow(month,year,firstDayOfMonth)
        tvMonthYear.text="${monthArray[month]}, $year"


        linearMonthYear.setOnClickListener {
            createDialogueBoxForMonthAndYear()
            alertDialog.show()
        }
        imageQRCodeScanner.setOnClickListener {
            val intentInitiator= IntentIntegrator(this)
                intentInitiator.initiateScan()
        }

    }

    fun attachCalendarView(currentDay: Int,calendar:Calendar,month: Int,year:Int){
        gifProgress.visibility=View.VISIBLE
        rvAttendance.visibility=View.GONE
       arrayList.clear()
        val lastDayOfPreviousMonth=getLastDayOFPreviousMonth(month,year)-1
        val dayOfMonth=calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for(i in (currentDay-1) downTo 0)
        {

            arrayList.add(lastDayOfPreviousMonth);
        }
        for(j in 1..dayOfMonth){
            arrayList.add(j)
        }
    }


    //Funtions to get the dates of previous month
    private fun getFirstDayOfCurrentMonth(month:Int, year:Int):Int{
        val calendar=Calendar.getInstance()
        calendar.set(year,month,1)
        var currentDay=calendar.get(Calendar.DAY_OF_WEEK)
        return --currentDay
    }
    private fun getLastDayOFPreviousMonth(month:Int, year:Int):Int{
        val calendar=Calendar.getInstance()
        var curMonth=month
        var curYear=year

        if(month!=0){
          curMonth-=1
        }
        else{
            curMonth=11
            curYear-=1
        }
        calendar.set(curYear,curMonth,1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun getDataForTheAttendanceAndShow(month: Int,year:Int,currentDay:Int){
        arrayAttendance=Array(31){0}
        val sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName), Context.MODE_PRIVATE)
        val gymId=sharedPreferences.getString("gymId","")!!
        val memberId=sharedPreferences.getString("member_id","")!!
        val dbref=FirebaseDatabase.getInstance().reference
        var newMonth=""
        if((month+1)<10){
            newMonth="0${month+1}"
        }

        Log.d("ASA","$year$newMonth  $memberId")
        val ref=dbref.child("Atten").child("MonkeyFitnessankushk2518").child(memberId).child("$year$newMonth")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@AttendanceShowActivity,"Something went wrong. Please try after sometime",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(snap in p0.children){
                    Log.d("ASA","here")
                    var index:Int=snap.key!!.toInt()
                    index--
                    Log.d("ASA","$index")
                    arrayAttendance[index]=snap.getValue(Int::class.java)!!
                }
                rvAttendance.adapter=AttendanceAdapter(arrayList,arrayAttendance,currentDay,this@AttendanceShowActivity)
                gifProgress.visibility=View.INVISIBLE
                rvAttendance.visibility=View.VISIBLE
                ref.removeEventListener(this)
            }
        })
    }
    private  fun createDialogueBoxForMonthAndYear(){
        var changedMonth=0
        var changedYear=0
        alertDialog=AlertDialog.Builder(this)
        val li=layoutInflater
        val view=li.inflate(R.layout.dialogue_choose_month,null,false)
        val spinnerMonth=view.spinnerMonth
        val spinnerYear=view.spinnerYear
        val monthAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,monthArray)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                  Toast.makeText(this@AttendanceShowActivity,parent!!.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show()
                changedMonth=position
            }
        }
        spinnerYear.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@AttendanceShowActivity,parent!!.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show()
               changedYear=parent.getItemAtPosition(position).toString().toInt()
            }
        }
        val yearAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,yearArray)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.adapter=yearAdapter
        spinnerMonth.adapter=monthAdapter

        alertDialog.setView(view)
        alertDialog.setPositiveButton("Select"
        ) { dialog, which ->
            val firstDayOfMonth=getFirstDayOfCurrentMonth(changedMonth,changedYear)
            val calendar=Calendar.getInstance()
            calendar.set(changedYear,changedMonth,1)
            tvMonthYear.text="${monthArray[changedMonth]}, $changedYear"
            attachCalendarView(firstDayOfMonth,calendar,changedMonth,changedYear)
            getDataForTheAttendanceAndShow(changedMonth,changedYear,firstDayOfMonth)


        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result != null) {
            if(result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(context, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();

                customDialogProgressBar.show()
                val sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),Context.MODE_PRIVATE)
                getAttendanceResponse(sharedPreferences.getString("member_id","")!!,result.contents)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
//        if ((requestCode==613)  and (resultCode== Activity.RESULT_OK )){
//            val gymId=sharedPreferences.getString("gymId","")!!
//            getNoticeResponse(gymId)
//        }
    }
    private fun getAttendanceResponse(member_id:String, gymId:String) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/attendance/").build()
        retrofit.create(ApiCalls::class.java).getAttendanceResult(member_id,gymId).enqueue(object:
            Callback<AttendanceModel> {
            override fun onFailure(call: Call<AttendanceModel>, t: Throwable) {
                Toast.makeText(this@AttendanceShowActivity, "It Seems that you are not connected to internet. Connect to internet and TRY AGAIN!!! " , Toast.LENGTH_LONG).show();
                customDialogProgressBar.dismiss()

            }

            override fun onResponse(call: Call<AttendanceModel>, response: Response<AttendanceModel>) {
                val attendanceModel=response.body()
                if(attendanceModel!!.isSuccess) {
                    val msgDiet = attendanceModel.msg
                    customDialogProgressBar.dismiss()
                    attendanceDoneAlertDialog.show()

                }
                else{
                    Toast.makeText(this@AttendanceShowActivity, "false " , Toast.LENGTH_LONG).show();
                }


            }
        })

    }

    private fun checkInAlertDialogue(){
        val layoutInflater=layoutInflater
        val view=layoutInflater.inflate(R.layout.alert_dialogue_attendance,null,false);

        view.animation = AnimationUtils.loadAnimation(this,R.anim.dual_scale)
        attendanceDoneAlertDialog=AlertDialog.Builder(this).create()
        attendanceDoneAlertDialog.setView(view)


    }


}
