package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_attendance.view.*

class AttendanceAdapter(private var arrayList: ArrayList<Int>,private var arrayAttendance:Array<Int>, private var previousDayCount:Int,var todayMonth:Int,var todayYear:Int,var todayDate:Int,var requiredMonth:Int,var requiredYear:Int, var context:Context) :
    RecyclerView.Adapter<AttendanceAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      var tvSingleDayOfMonth:TextView=itemView.tvSingleDayOfMonth
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.adapter_attendance, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvSingleDayOfMonth.text = arrayList[position].toString()
        if(position<previousDayCount){
            holder.tvSingleDayOfMonth.setTextColor(context.resources.getColor(R.color.colorTextLight))
        }
        else{
            if(isGivenDateSmaller(position-previousDayCount+1)) {
                if (arrayAttendance[position - previousDayCount] == 1)
                    holder.tvSingleDayOfMonth.setBackgroundResource(R.drawable.modified_green_indicator)
                else if (arrayAttendance[position - previousDayCount] == 0)
                    holder.tvSingleDayOfMonth.setBackgroundResource(R.drawable.modified_red_indicator)
            }
            else{
                holder.tvSingleDayOfMonth.setTextColor(context.resources.getColor(R.color.colorTextLight))
            }
        }
        if(((position-previousDayCount+1)==todayDate) and (todayMonth==requiredMonth) and (todayYear==requiredYear)) {

           Log.d("AA","$todayDate  $todayMonth  $todayYear  $requiredMonth  $requiredYear  $previousDayCount  ${position-previousDayCount-1}" )
            holder.tvSingleDayOfMonth.setTextColor(context.resources.getColor(R.color.colorBlack))
            holder.tvSingleDayOfMonth.setBackgroundResource(R.drawable.modified_blue_indicator)
        }

    }
    fun isGivenDateSmaller(requiredDate:Int):Boolean{
        return if(todayYear==requiredYear){
            if(todayMonth==requiredMonth) {
                todayDate>=requiredDate
            } else todayMonth>requiredMonth
        } else todayYear>requiredYear
    }
}