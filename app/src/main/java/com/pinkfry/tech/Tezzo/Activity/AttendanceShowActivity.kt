package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.pinkfry.tech.Tezzo.Adapter.AttendanceAdapter
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.activity_attendance_show.*
import kotlinx.android.synthetic.main.dialogue_choose_month.view.*
import java.util.*
import kotlin.collections.ArrayList

class AttendanceShowActivity : AppCompatActivity(){
    lateinit var arrayList:ArrayList<Int>
     val arrayAttendance=Array<Int>(31){0}
    val month= arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
    val year= arrayOf("2020","2021","2022","2023","2024","2025","2026")
    lateinit var  alertDialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_show)
        window.statusBarColor=getColor(R.color.backgroundColor)
        arrayList= ArrayList()
        val calendar=Calendar.getInstance()
        val month=calendar.get(Calendar.MONTH)
        val year=calendar.get(Calendar.YEAR)
        val currentDay=getFirstDayOfCurrentMonth(month,year)
        attachCalendarView(currentDay,calendar,month,year)
        rvAttendance.layoutManager=GridLayoutManager(this,7)

          getDataForTheAttendanceAndShow(month,year,currentDay)
        linearMonthYear.setOnClickListener {
            createDialogueBoxForMonthAndYear()
            alertDialog.show()
        }

    }

    fun attachCalendarView(currentDay: Int,calendar:Calendar,month: Int,year:Int){
       arrayList.clear()
        for(i in (currentDay-1) downTo 0)
        {

            arrayList.add(getLastDayOFPreviousMonth(month,year)-i);
        }
        for(j in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
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
                ref.removeEventListener(this)
            }
        })
    }
    private  fun createDialogueBoxForMonthAndYear(){
        alertDialog=AlertDialog.Builder(this)
        val li=layoutInflater
        val view=li.inflate(R.layout.dialogue_choose_month,null,false)
        val spinnerMonth=view.spinnerMonth
        val spinnerYear=view.spinnerYear
        val monthAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,month)
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

            }
        }
        val yearAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,year)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.adapter=yearAdapter
        spinnerMonth.adapter=monthAdapter

        alertDialog.setView(view)
        alertDialog.setPositiveButton("Select"
        ) { dialog, which ->

        }
    }


}
