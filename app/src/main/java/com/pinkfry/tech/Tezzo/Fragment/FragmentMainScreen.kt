package com.pinkfry.tech.Tezzo.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.annotations.NotNull
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPGService
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import com.pinkfry.tech.Tezzo.Activity.*
import com.pinkfry.tech.Tezzo.Adapter.NoticeAdapter
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.NoticeModel
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.content_main.view.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class FragmentMainScreen: Fragment() {
    lateinit var alertDialog: AlertDialog;
    lateinit var customDialogProgressBar:CustomDialogeProgressBar
    lateinit var sharedPreferences:SharedPreferences
    var TAG="FMS";
    val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customDialogProgressBar=CustomDialogeProgressBar(context)
        customDialogProgressBar.setCancelable(false)
        customDialogProgressBar.window!!.setBackgroundDrawableResource(android.R.color.transparent);
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.content_main,container,false)

           view.cardDiet.setOnClickListener {
               var intent=Intent(activity,DietPlanActivity::class.java)
               startActivity(intent)
           }
        view.cardAttendance.setOnClickListener {
//            val intentInitiator=IntentIntegrator.forSupportFragment(this)
//                intentInitiator.initiateScan()
            val intent=Intent(activity,AttendanceShowActivity::class.java)
            startActivity(intent)

        }
        Log.d("FMS","I am Here")
        view.cardWorkout.setOnClickListener {
            var intent=Intent(activity,WorkOutActivity::class.java)
            startActivity(intent)
        }
        view.cardPayment.setOnClickListener {
//           customDialogProgressBar.show();
//            makePaymentUsingPaytm()
            var intent=Intent(activity,PaymentStartingActivity::class.java)
            startActivity(intent)
        }
       view.rvNotice.layoutManager=LinearLayoutManager(context)
         sharedPreferences=requireActivity().getSharedPreferences(resources.getString(R.string.packageName),Context.MODE_PRIVATE)

        getNoticeResponse(sharedPreferences.getString("gymId","")!!)
        view.tvRetry.setOnClickListener {
            customDialogProgressBar.show()
            getNoticeResponse(sharedPreferences.getString("gymId","")!!)
        }
        return view

    }

    private fun getNoticeResponse(gymId: String){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.tezzo.fit/notice/").build()
        retrofit.create(ApiCalls::class.java).getNoticeData(gymId).enqueue(object :Callback<ArrayList<NoticeModel>>{
            override fun onFailure(call: Call<ArrayList<NoticeModel>>, t: Throwable) {
//                Toast.makeText(context, "failed " , Toast.LENGTH_LONG).show();
//                val intent=Intent(activity,NoInternetScreen::class.java)
//                startActivityForResult(intent,613);
                customDialogProgressBar.dismiss()
                  view!!.linearNoticeNoInterent.visibility=View.VISIBLE
            }

            override fun onResponse(call: Call<ArrayList<NoticeModel>>, response: Response<ArrayList<NoticeModel>>) {
               val noticeModel= response.body()!!
                if(view==null){
                    Toast.makeText(context,"Something went wrong. Restart and Try Again",Toast.LENGTH_SHORT).show()
                }
                else {
                    view!!.linearNoticeNoInterent.visibility = View.GONE
                    customDialogProgressBar.dismiss()
                    view!!.rvNotice.adapter = NoticeAdapter(noticeModel, context!!);
                }
            }
        })
    }
}