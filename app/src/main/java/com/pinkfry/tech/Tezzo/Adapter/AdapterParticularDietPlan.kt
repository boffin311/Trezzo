package com.pinkfry.tech.Tezzo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.DietModel.DietItem
import com.pinkfry.tech.Tezzo.Model.DietModel.MsgDiet
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adapter_upper_type_diet.view.*
import kotlinx.android.synthetic.main.adapter_upper_type_diet.view.rvTimeBased

class AdapterParticularDietPlan(
    private var dietResponse: ArrayList<MsgDiet>,
    var context: Context
) : RecyclerView.Adapter<AdapterParticularDietPlan.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvTimeBased: RecyclerView = itemView.rvTimeBased
        val tvDietName: TextView = itemView.tvDietName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.adapter_upper_type_diet, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return dietResponse.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val msgDiet = dietResponse[position]
        val dietItem:ArrayList<DietItem> = msgDiet.diet  as ArrayList<DietItem>
        holder.tvDietName.text=msgDiet.dietName
        holder.rvTimeBased.layoutManager=LinearLayoutManager(context)
        holder.rvTimeBased.adapter=DietTimeAdapter(dietItem,context)

    }
}