package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.MsgWorkOut
import com.pinkfry.tech.Tezzo.Model.SingleDayWorkoutModel
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_upper_type_workout.view.*

class AdapterParticularWorkoutPlan(
    private var dietResponse: ArrayList<MsgWorkOut>,
    var context: Context
) : RecyclerView.Adapter<AdapterParticularWorkoutPlan.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvDayBased: RecyclerView = itemView.rvDayBased
        val tvPlanName: TextView = itemView.tvPlanName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.adapter_upper_type_workout, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return dietResponse.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val msgDiet = dietResponse[position]
        val singleDayWorkoutModel: ArrayList<SingleDayWorkoutModel> =
            msgDiet.workout as ArrayList<SingleDayWorkoutModel>
        holder.tvPlanName.text = msgDiet.planName
        holder.rvDayBased.layoutManager = LinearLayoutManager(context)
        holder.rvDayBased.adapter = WorkoutDayAdapter(singleDayWorkoutModel, context)

    }
}