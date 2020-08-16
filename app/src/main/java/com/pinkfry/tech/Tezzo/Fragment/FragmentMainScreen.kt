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
import com.pinkfry.tech.Tezzo.Activity.AttendanceShowActivity
import com.pinkfry.tech.Tezzo.Activity.DietPlanActivity
import com.pinkfry.tech.Tezzo.Activity.PaymentSuccessActivity
import com.pinkfry.tech.Tezzo.Activity.WorkOutActivity
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
           customDialogProgressBar.show();
            makePaymentUsingPaytm()
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

    fun makePaymentUsingPaytm() {
        val okHttpClient = OkHttpClient()
        val jsonValue = JSONObject()
        try {
            Log.d(TAG, "onCreate: " + requireActivity().intent.getIntExtra("originalprice", 0))
            jsonValue.put("TXN_AMOUNT", 1)
            //if you make the authentication than also send cust Id to server
//            jsonValue.put("CUST_ID",FirebaseAuthException.)
            Log.d(TAG, "onCreate: $jsonValue")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "onCreate: $jsonValue")
        val requestBody: RequestBody = RequestBody.create(JSON,jsonValue.toString())
        val request: Request = Request.Builder()
            .url("https://checksum-generator-pinkfry.herokuapp.com/generate_checksum")
            .post(requestBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
           override fun onFailure(
                @NotNull call: okhttp3.Call,
                @NotNull e: IOException?
            ) {
               customDialogProgressBar.dismiss()
            }

            @Throws(IOException::class)
            override fun onResponse(
                @NotNull call: okhttp3.Call,
                @NotNull response: okhttp3.Response
            ) {
                //  Log.d(TAG, "onResponse: "+response.body().string());
                val MID: String
                val ORDER_ID: String
                val CUST_ID: String
                val INDUSTRY_TYPE_ID: String
                val CHANNEL_ID: String
                val TXN_AMOUNT: String
                val WEBSITE: String
                val CALLBACK_URL: String
                val CHECKSUMHASH: String
                try {
                    val jsonObject1 = JSONObject(response.body()!!.string())
                    MID = jsonObject1.getString("MID")
                    ORDER_ID = jsonObject1.getString("ORDER_ID")
                    Log.d(TAG, "onResponse: $ORDER_ID")
                    CUST_ID = jsonObject1.getString("CUST_ID")
                    Log.d(TAG, "onResponse: $CUST_ID")
                    INDUSTRY_TYPE_ID = jsonObject1.getString("INDUSTRY_TYPE_ID")
                    Log.d(TAG, "onResponse: $INDUSTRY_TYPE_ID")
                    CHANNEL_ID = jsonObject1.getString("CHANNEL_ID")
                    Log.d(TAG, "onResponse: $CHANNEL_ID")
                    TXN_AMOUNT = jsonObject1.getString("TXN_AMOUNT")
                    WEBSITE = jsonObject1.getString("WEBSITE")
                    CALLBACK_URL = jsonObject1.getString("CALLBACK_URL")
                    CHECKSUMHASH = jsonObject1.getString("CHECKSUMHASH")
                    Log.d(TAG, "onResponse: $CHECKSUMHASH")
                    val paytmPGService: PaytmPGService = PaytmPGService.getProductionService()
                    val paramMap =
                        HashMap<String, String>()
                    paramMap["MID"] = MID
                    paramMap["ORDER_ID"] = ORDER_ID
                    paramMap["CUST_ID"] = CUST_ID
                    paramMap["INDUSTRY_TYPE_ID"] = INDUSTRY_TYPE_ID
                    paramMap["CHANNEL_ID"] = CHANNEL_ID
                    paramMap["TXN_AMOUNT"] = TXN_AMOUNT
                    paramMap["WEBSITE"] = WEBSITE
                    paramMap["CALLBACK_URL"] = CALLBACK_URL
                    paramMap["CHECKSUMHASH"] = CHECKSUMHASH
                    val Order = PaytmOrder(paramMap)
                    customDialogProgressBar.dismiss()
                    paytmPGService.initialize(Order, null)
                    paytmPGService.startPaymentTransaction(
                        activity,
                        true,
                        true,
                        object : PaytmPaymentTransactionCallback {
                            override fun onTransactionResponse(inResponse: Bundle) {
                                Log.d(TAG, "onTransactionResponse: $inResponse")
                                val status=inResponse.getString("STATUS");

                                activity!!.runOnUiThread {
                                    if (status == "TXN_SUCCESS") {
                                        Toast.makeText(
                                            activity,
                                            "Payment successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent =
                                            Intent(activity, PaymentSuccessActivity::class.java)
                                        intent.putExtra("inResponse", inResponse)
                                        intent.putExtra("status", 1)
                                        startActivity(intent)
                                    } else {

                                        Toast.makeText(
                                            activity,
                                            "Payment Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent =
                                            Intent(activity, PaymentSuccessActivity::class.java)
                                        intent.putExtra("inResponse", inResponse)
                                        intent.putExtra("status", 0);
                                        startActivity(intent)

                                    }
                                }
//                                val ORDERID = inResponse.getString("ORDERID")
//                                Log.d(TAG, "onTransactionResponse: $ORDERID")
//                                val jsonObject = JSONObject()
//                                try {
//                                    jsonObject.put("ORDERID", ORDERID)
//                                } catch (e: JSONException) {
//                                    e.printStackTrace()
//                                }
//                                val requestBody: RequestBody =
//                                    RequestBody.create(JSON,jsonObject.toString())
//                                val request: Request = Request.Builder()
//                                    .url("https://checksum-generator-pinkfry.herokuapp.com/gettxnstatus")
//                                    .post(requestBody)
//                                    .build()
//                                okHttpClient.newCall(request)
//                                    .enqueue(object : okhttp3.Callback {
//                                        override fun onFailure(
//                                            @NotNull call: okhttp3.Call,
//                                            @NotNull e: IOException?
//                                        ) {
//                                            Log.d(TAG, "onFailure: ")
//                                        }
//
//                                        @Throws(IOException::class)
//                                        override fun onResponse(
//                                            @NotNull call: okhttp3.Call,
//                                            @NotNull response: okhttp3.Response
//                                        ) {
////                                             Log.d(TAG, "onRes: " + response.body()!!.string());
//                                            try {
//                                                val jsonobjectFinalResult =
//                                                    JSONObject(response.body()!!.string())
//                                                Log.d(TAG, "onRes: $jsonobjectFinalResult");
//                                                val finalStatus: String =
//                                                    jsonobjectFinalResult.getString("STATUS")
//                                                Log.d(
//                                                    TAG,
//                                                    "onResponse: $finalStatus"
//                                                )
//                                                when (finalStatus) {
//                                                    "TXN_SUCCESS" -> {
//
////                                                        val view: View =
////                                                            layoutInflater.inflate(
////                                                                R.layout.alert_order_successful,
////                                                                null,
////                                                                false
////                                                            )
////                                                        activity!!.runOnUiThread {
////                                                            Toast.makeText(activity,"Payment successful",Toast.LENGTH_SHORT).show()
////                                                            val intent=Intent(activity, AttendanceShowActivity::class.java)
////                                                            startActivity(intent)
////
////                                                            //  buildAlertDialogueBox(view);
//////                                                            Snackbar.make(
//////                                                                view,
//////                                                                "Please wait while your booking confirms",
//////                                                                Snackbar.LENGTH_INDEFINITE
//////                                                            ).show()
//////                                                            customerdetails("PAYTM")
////                                                        }
//                                                    }
//                                                }
//                                            } catch (e: JSONException) {
//                                                e.printStackTrace()
//                                            }
//                                        }
//                                    })
                            }

                           override fun networkNotAvailable() {
                                Log.d(TAG, "networkNotAvailable: ")
                            }

                           override fun clientAuthenticationFailed(inErrorMessage: String?) {
                                Log.d(TAG, "clientAuthenticationFailed: ")
                            }

                           override fun someUIErrorOccurred(inErrorMessage: String?) {
                                Log.d(TAG, "someUIErrorOccurred: ")
                            }

                            override fun onErrorLoadingWebPage(
                                iniErrorCode: Int,
                                inErrorMessage: String?,
                                inFailingUrl: String?
                            ) {
                                Log.d(TAG, "onErrorLoadingWebPage: ")
                            }

                           override fun onBackPressedCancelTransaction() {
                                Log.d(TAG, "onBackPressedCancelTransaction: ")
                            }

                            override fun onTransactionCancel(
                                inErrorMessage: String?,
                                inResponse: Bundle?
                            ) {
                                Log.d(TAG, "onTransactionCancel: ")
                            }
                        })
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}