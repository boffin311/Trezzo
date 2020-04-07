package com.pinkfry.tech.Tezzo.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adatper_notice.view.*

class NoticeAdapter(private var arrayList:ArrayList<String>):RecyclerView.Adapter<NoticeAdapter.MyHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var li=parent.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view=li.inflate(R.layout.adatper_notice,parent,false)
        return  MyHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
       holder.tvSingleNotice.text= "$position ${arrayList[position]}"
    }

    class MyHolder(itemview:View) : RecyclerView.ViewHolder(itemview){
      var tvSingleNotice=itemview.tvSingleNotice;
    }
}