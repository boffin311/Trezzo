package com.pinkfry.tech.Tezzo.Fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pinkfry.tech.Tezzo.Activity.MainActivity
import com.pinkfry.tech.Tezzo.Model.LoginModel
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import kotlinx.android.synthetic.main.frament_get_started.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentLogin :Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = activity!!.window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity!!.window.statusBarColor=resources.getColor(R.color.backgroundColor)
//            } else {
//                // We want to change tint color to white again.
//                // You can also record the flags in advance so that you can turn UI back completely if
//                // you have set other flags before, such as translucent or full screen.
//                decor.systemUiVisibility = 0
//            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_log_in,container,false)
        view.btngetStarted.setOnClickListener {
            val mobileNo = view.etMobileNumber.text.toString()
            val gymId = view.etGymId.text.toString()
            if (mobileNo.isNotEmpty() and gymId.isNotEmpty()) {
                getDataFromLogin(mobileNo, gymId)

            } else {
                if(mobileNo.isEmpty()){
                    view.etMobileNumber.error = "Enter Mobile Number"

                }
                if(gymId.isEmpty()){

                    view.etGymId.error = "Enter gymId"
                }
            }
        }
        return view
    }
    fun getDataFromLogin(mobileNo:String,gymId:String){
        var retrofit= Retrofit.Builder().baseUrl("https://api.tezzo.fit/member/").addConverterFactory(
            GsonConverterFactory.create()).build()
        retrofit.create(ApiCalls::class.java).getLoginData(mobileNo,gymId).enqueue(object :
            Callback<LoginModel> {
            override fun onFailure(call: Call<LoginModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                val loginModel=response.body()!!
                val isSuccessFull=loginModel.isSuccess
                if(isSuccessFull){
                    var msgItem=loginModel.msg[0]
                    Log.d("LA",msgItem.toString())
                    var sharedPreferences=activity!!.getSharedPreferences(resources.getString(R.string.packageName),
                        Context.MODE_PRIVATE)
                    val editor=sharedPreferences.edit()
                    editor.putString("firstName",msgItem.firstname)
                    editor.putString("lastName",msgItem.lastname)
                    editor.putString("address",msgItem.address)
                    editor.putString("email",msgItem.email)
                    editor.putString("weight",msgItem.weight)
                    editor.putString("height",msgItem.height)
                    editor.putString("dob",msgItem.dob)
                    editor.putString("doj",msgItem.doj)
                    editor.putString("gender",msgItem.gender)
                    editor.putString("mobile_no",msgItem.mobileNo)
                    editor.putString("gymId",msgItem.gymId)
                    editor.putString("membershipNo",msgItem.membershipNo)
                    editor.putString("disease",msgItem.disease)
                    editor.apply()
                    Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show()
                    var intent= Intent(activity, MainActivity::class.java)
                    activity!!.finish()
                    startActivity(intent)
                }
                else{
                    Toast.makeText(context,"User not found ",Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}