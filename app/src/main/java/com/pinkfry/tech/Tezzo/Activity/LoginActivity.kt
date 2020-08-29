package com.pinkfry.tech.Tezzo.Activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.pinkfry.tech.Tezzo.Fragment.FragmentGetStarted
import com.pinkfry.tech.Tezzo.R

class LoginActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor=resources.getColor(R.color.colorPrimary)
        setContentView(R.layout.activity_login)



        var ftrxn=supportFragmentManager.beginTransaction()
        ftrxn.add(R.id.frameLogin,FragmentGetStarted())
        ftrxn.commit()
//        btngetStarted.setOnClickListener {
//            val mobileNo = etMobileNumber.text.toString()
//            val gymId = etGymId.text.toString()
//            if (mobileNo.isNotEmpty() and gymId.isNotEmpty()) {
//                getDataFromLogin(mobileNo, gymId)
//
//            } else {
//              if(mobileNo.isEmpty()){
//                  etMobileNumber.error = "Enter Mobile Number"
//
//                    }
//                if(gymId.isEmpty()){
//
//                    etGymId.error = "Enter gymId"
//                }
//            }
//        }

    }
//    fun getDataFromLogin(mobileNo:String,gymId:String){
//    var retrofit=Retrofit.Builder().baseUrl("https://api.tezzo.fit/member/").addConverterFactory(GsonConverterFactory.create()).build()
//        retrofit.create(ApiCalls::class.java).getLoginData(mobileNo,gymId).enqueue(object :Callback<LoginModel>{
//            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
//              val loginModel=response.body()!!
//               val isSuccessFull=loginModel.isSuccess
//                if(isSuccessFull){
//                    var msgItem=loginModel.msg[0]
//                    Log.d("LA",msgItem.toString())
//                    var sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),Context.MODE_PRIVATE)
//                    val editor=sharedPreferences.edit()
//                    editor.putString("firstName",msgItem.firstname)
//                    editor.putString("lastName",msgItem.lastname)
//                    editor.putString("address",msgItem.address)
//                    editor.putString("email",msgItem.email)
//                    editor.putString("weight",msgItem.weight)
//                    editor.putString("height",msgItem.height)
//                    editor.putString("dob",msgItem.dob)
//                    editor.putString("doj",msgItem.doj)
//                    editor.putString("gender",msgItem.gender)
//                    editor.putString("mobile_no",msgItem.mobileNo)
//                    editor.putString("gymId",msgItem.gymId)
//                    editor.putString("membershipNo",msgItem.membershipNo)
//                    editor.putString("disease",msgItem.disease)
//                    editor.apply()
//                    Toast.makeText(this@LoginActivity,"Login Successful",Toast.LENGTH_SHORT).show()
//                    var intent=Intent(this@LoginActivity,MainActivity::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this@LoginActivity,"User not found ",Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        })
//    }
}
