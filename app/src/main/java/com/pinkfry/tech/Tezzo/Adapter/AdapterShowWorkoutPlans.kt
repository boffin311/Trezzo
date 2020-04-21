package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Activity.WorkOutActivity
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_show_workout_plans.view.*

class AdapterShowWorkoutPlans(var arrayList: ArrayList<String>,var currentPosition: Int,var context:Context) :
    RecyclerView.Adapter<AdapterShowWorkoutPlans.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvWorkoUtPlanName: TextView = itemView.tvWorkoutPlanName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = li.inflate(R.layout.adapter_show_workout_plans, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvWorkoUtPlanName.text= arrayList[position]
        if(currentPosition==position){
        holder.tvWorkoUtPlanName.setBackgroundResource(R.drawable.modified_rounded_primary_color)
        holder.tvWorkoUtPlanName.setTextColor(context.resources.getColor(R.color.colorWhite))
        }
        else{
            holder.tvWorkoUtPlanName.setBackgroundResource(R.drawable.modified_rounded_button)
            holder.tvWorkoUtPlanName.setTextColor(context.resources.getColor(R.color.colorBlack))
        }


        holder.tvWorkoUtPlanName.setOnClickListener {
            currentPosition=position
            WorkOutActivity.mutableScroll.value=position
            this.notifyDataSetChanged()
        }

    }
}