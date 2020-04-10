package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.DietItem
import com.pinkfry.tech.Tezzo.Model.ItemItem
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_time_based.view.*

class DietTimeAdapter(var dietItemArray:ArrayList<DietItem>,var context:Context): RecyclerView.Adapter<DietTimeAdapter.MyHolder>(){

    class MyHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvTime: TextView =itemView.tvTime
        val rvALlDiets:RecyclerView=itemView.rvAllDiets
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=layoutInflater.inflate(R.layout.adapter_time_based,parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return  dietItemArray.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvTime.animation=AnimationUtils.loadAnimation(context,R.anim.recycler_flip);
        holder.tvTime.text=dietItemArray[position].time
        val itemArray=dietItemArray[position].item as ArrayList<ItemItem>
        holder.rvALlDiets.layoutManager=LinearLayoutManager(context)
        holder.rvALlDiets.adapter=DietItemAdapter(itemArray,context)
    }
}