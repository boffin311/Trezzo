package com.pinkfry.tech.Tezzo.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.WorkOutModel.SingleDayWorkoutModel
import com.pinkfry.tech.Tezzo.Model.WorkOutModel.WorkoutItem
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_day_bases_workout.view.*

class WorkoutDayAdapter(var workoutItemArray:ArrayList<SingleDayWorkoutModel>, var context:Context): RecyclerView.Adapter<WorkoutDayAdapter.MyHolder>(){

    class MyHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvDayWorkout: TextView =itemView.tvDayWorkOut
        val rvAllDays:RecyclerView=itemView.rvAllDays
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=layoutInflater.inflate(R.layout.adapter_day_bases_workout,parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return  workoutItemArray.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvDayWorkout.animation=AnimationUtils.loadAnimation(context,R.anim.recycler_flip);
        holder.tvDayWorkout.text="${workoutItemArray[position].workoutDay} ( ${workoutItemArray[position].bodyPart[0][0]} )"
        val itemArray=workoutItemArray[position].workout as ArrayList<WorkoutItem>
        holder.rvAllDays.layoutManager=LinearLayoutManager(context)
        holder.rvAllDays.adapter=WorkoutItemAdapter(itemArray,context)
    }
}