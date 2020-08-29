package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.WorkOutModel.WorkoutItem
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_single_work_out.view.*

class WorkoutItemAdapter(var itemArrayList:ArrayList<WorkoutItem>, var context: Context) : RecyclerView.Adapter<WorkoutItemAdapter.MyHolder>() {
    class MyHolder(  itemView: View):RecyclerView.ViewHolder(itemView) {
    val tvWorkOutName:TextView=itemView.tvWorkoutName
        val relativeLayoutSingleWorkout:RelativeLayout=itemView.relativeLayoutSingleWorkOut;
        val tvSetCount:TextView =itemView.tvSetCount
        val tvWorkoutReps: TextView =itemView.tvWorkOutReps
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=layoutInflater.inflate(R.layout.adapter_single_work_out,parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
       return  itemArrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var workOutItem=itemArrayList[position]
        holder.relativeLayoutSingleWorkout.animation= AnimationUtils.loadAnimation(context,R.anim.recycler_flip)
        holder.tvSetCount.animation=AnimationUtils.loadAnimation(context,R.anim.translate_positive_right)
        holder.tvSetCount.text = workOutItem.workoutSets.toString()
          holder.tvWorkOutName.text=workOutItem.workoutName
        holder.tvWorkoutReps.text = workOutItem.workoutReps
    }
}