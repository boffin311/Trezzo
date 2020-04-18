package com.pinkfry.tech.Tezzo.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkfry.tech.Tezzo.Adapter.AttendanceAdapter
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.activity_attendance_show.*
import java.util.*
import kotlin.collections.ArrayList

class AttendanceShowActivity : AppCompatActivity() {
    lateinit var arrayList:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_show)
        window.statusBarColor=getColor(R.color.backgroundColor)
        arrayList= ArrayList()

        val calendar=Calendar.getInstance()
        val month=calendar.get(Calendar.MONTH)
        val year=calendar.get(Calendar.YEAR)
        var currentDay=getFirstDayOfCurrentMonth(month,year)
        for(i in (currentDay-1) downTo 0)
        {

            arrayList.add(getLastDayOFPreviousMonth(month,year)-i);
        }
        for(j in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            arrayList.add(j)
        }

        rvAttendance.layoutManager=GridLayoutManager(this,7)
        rvAttendance.adapter=AttendanceAdapter(arrayList,currentDay,this)
    }
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
}
