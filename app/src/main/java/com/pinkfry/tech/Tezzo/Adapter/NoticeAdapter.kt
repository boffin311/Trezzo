package com.pinkfry.tech.Tezzo.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.pinkfry.tech.Tezzo.Model.NoticeModel
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.adatper_notice.view.*

class NoticeAdapter(private var arrayList:ArrayList<NoticeModel>,var context:Context):RecyclerView.Adapter<NoticeAdapter.MyHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var li=parent.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view=li.inflate(R.layout.adatper_notice,parent,false)
        return  MyHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        var noticeModel=arrayList[position]
        holder.linearNotice.animation=AnimationUtils.loadAnimation(context,R.anim.translate_y_bottom)
       holder.tvDescription.text= noticeModel.description
        holder.tvTitleNotice.text=noticeModel.title
        holder.tvDateNotice.text=noticeModel.date
    }

    class MyHolder(itemview:View) : RecyclerView.ViewHolder(itemview){
      var tvDescription= itemview.tvDescriptionNotice!!
        var tvDateNotice=itemview.tvDateNotice!!
        var tvTitleNotice=itemview.tvTitleNotice!!
        var linearNotice: LinearLayout =itemview.linearNotice
    }
}