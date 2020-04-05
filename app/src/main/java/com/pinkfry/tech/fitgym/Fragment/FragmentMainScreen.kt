package com.pinkfry.tech.fitgym.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkfry.tech.fitgym.Adapter.NoticeAdapter
import com.pinkfry.tech.fitgym.R
import kotlinx.android.synthetic.main.content_main.*

class FragmentMainScreen: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.content_main,container,false)
       rvNotice.layoutManager=LinearLayoutManager(context)
        var arrayList= arrayListOf<String>("Important Notice regarding suspension of classes  Download","Admission Notice 2020-2021 Download","MBA Admission 2020-2021 Download","Date sheet for mid term examination March 2020(Reappear) Download");
        rvNotice.adapter=NoticeAdapter(arrayList);
        return view
    }
}