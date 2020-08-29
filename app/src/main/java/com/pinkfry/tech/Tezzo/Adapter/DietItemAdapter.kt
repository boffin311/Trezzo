package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.DietModel.ItemItem
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_all_diets.view.*

class DietItemAdapter(var itemArrayList:ArrayList<ItemItem>, var context: Context) : RecyclerView.Adapter<DietItemAdapter.MyHolder>() {
    class MyHolder(  itemView: View):RecyclerView.ViewHolder(itemView) {
    val tvDietName:TextView=itemView.tvDietName
        val relativeLayoutSingleDiet=itemView.relativeLayoutSingleDiet;
        val imageDietLogo=itemView.imageDietLogo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=layoutInflater.inflate(R.layout.adapter_all_diets,parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
       return  itemArrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.relativeLayoutSingleDiet.animation= AnimationUtils.loadAnimation(context,R.anim.recycler_flip)
        holder.imageDietLogo.animation=AnimationUtils.loadAnimation(context,R.anim.translate_positive_right)
          holder.tvDietName.text=itemArrayList[position].value
    }
}