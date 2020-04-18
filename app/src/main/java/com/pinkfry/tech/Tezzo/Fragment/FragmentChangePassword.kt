package com.pinkfry.tech.Tezzo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.fragment_change_password.view.etConfirmPassword

class FragmentChangePassword : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_change_password,container,false)
        view.btnChangePassword.setOnClickListener {
            val changedPassword=view.etEnterPassword.text.toString()
            val confirmPassword=view.etConfirmPassword.text.toString()
            if(changedPassword.isEmpty()){
                view.etEnterPassword.error = "This field can't be empty"
            }else if(confirmPassword.isEmpty()){
               view.etConfirmPassword.error="This field can't be empty"
            }
            else if (changedPassword != confirmPassword){
               view.tvPasswordNotMatch.visibility=View.VISIBLE
            }
            else{
                //Request marni hai server pe

            }
        }
        view.tvSkip.setOnClickListener {

        }
        return view
    }
}