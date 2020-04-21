package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Activity.DietPlanActivity
import com.pinkfry.tech.Tezzo.Activity.WorkOutActivity
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_show_diet_plan.view.*

class AdapterShowDietPlan(var arrayList: ArrayList<String>, var currentPosition: Int, var context:Context) :
    RecyclerView.Adapter<AdapterShowDietPlan.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDietPlanName: TextView = itemView.tvDietPlanName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = li.inflate(R.layout.adapter_show_diet_plan, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvDietPlanName.text= arrayList[position]
        if(currentPosition==position){
        holder.tvDietPlanName.setBackgroundResource(R.drawable.modified_rounded_primary_color)
        holder.tvDietPlanName.setTextColor(context.resources.getColor(R.color.colorWhite))
        }
        else{
            holder.tvDietPlanName.setBackgroundResource(R.drawable.modified_rounded_button)
            holder.tvDietPlanName.setTextColor(context.resources.getColor(R.color.colorBlack))
        }


        holder.tvDietPlanName.setOnClickListener {
            currentPosition=position
            DietPlanActivity.mutableScroll.value=position
            this.notifyDataSetChanged()
        }

    }
}