package com.pinkfry.tech.Tezzo.Fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkfry.tech.Tezzo.Adapter.NoticeAdapter
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.content_main.view.*


class FragmentMainScreen: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.content_main,container,false)

       view.rvNotice.layoutManager=LinearLayoutManager(context)
        var arrayList= arrayListOf<String>("Important Notice regarding suspension of classes  Download","Admission Notice 2020-2021 Download","MBA Admission 2020-2021 Download","Date sheet for mid term examination March 2020(Reappear) Download");
        view.rvNotice.adapter=NoticeAdapter(arrayList);
        return view
    }
}