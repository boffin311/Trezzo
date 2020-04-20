package com.pinkfry.tech.Tezzo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.frament_get_started.view.*

class FragmentGetStarted : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frament_get_started,container,false)
        view.frameGetStared.setOnClickListener{
            var frgTrxn=requireActivity().supportFragmentManager.beginTransaction()
            frgTrxn.replace(R.id.frameLogin,FragmentLogin())
            frgTrxn.commit()
        }
        return view
    }
}