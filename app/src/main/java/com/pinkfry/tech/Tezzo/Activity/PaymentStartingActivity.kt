package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.database.annotations.NotNull
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPGService
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.PlanModel.DataItem
import com.pinkfry.tech.Tezzo.Model.PlanModel.PlanResponse
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_payment_starting.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class PaymentStartingActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    var planPrice: Int = 0;
    var planName: String="";
    var isSelected = false;
    var size=0;
    lateinit var customDialogProgressBar: CustomDialogeProgressBar
    lateinit var dataitems: List<DataItem>
    val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

    val TAG = "PSA";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_starting)
        sharedPreferences =
            getSharedPreferences(resources.getString(R.string.packageName), Context.MODE_PRIVATE)
        customDialogProgressBar = CustomDialogeProgressBar(this)
        customDialogProgressBar.setCancelable(false)
        customDialogProgressBar.window!!.setBackgroundDrawableResource(android.R.color.transparent);


        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tezzo.fit/plan/").build()
        retrofit.create(ApiCalls::class.java).getPlans(sharedPreferences.getString("gymId", ""))
            .enqueue(object : Callback<PlanResponse> {
                override fun onFailure(call: Call<PlanResponse>, t: Throwable) {
                    Toast.makeText(
                        this@PaymentStartingActivity,
                        "Something went wrong, Check your Network Connection and try Again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<PlanResponse>,
                    response: Response<PlanResponse>
                ) {
                    var planResponse = response.body()!!
                    if (planResponse.isSuccess) {
                        dataitems = planResponse.data
                        size=dataitems.size;
                        for ((index, ele) in dataitems.withIndex()) {
                            val plan = "${ele.planName} ( ₹${ele.planPrice} )"
                            makeRadioButton(plan, false, index)
                        }
                    }

                }
            })
        planRadioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                Log.d(TAG, "${(checkedId-1)%size} ");
                planPrice = dataitems[(checkedId-1)%size].planPrice
                planName=planNameToNumberString(dataitems[(checkedId-1)%size].planName)
                Log.d(TAG,"$planPrice")
//               val id= group!!.checkedRadioButtonId
//                val radioButton=findViewById<RadioButton>(id)
//                Log.d(TAG,"${radioButton.text}")
                isSelected = true;
            }
        })
        btnPayNow.setOnClickListener {

            if (isSelected) {
                customDialogProgressBar.show()
//                planPrice=1;
                makePaymentUsingPaytm(planPrice)
            }
            else Toast.makeText(this, "No plan selected", Toast.LENGTH_SHORT).show()
        }
    }
    fun planNameToNumberString(planName:String):String{
        var i=0;
        while(planName[i]!=' '){
            i++
        }
        return (planName.substring(0,i))
    }
    fun makeRadioButton(plan: String, isCheck: Boolean, index: Int) {
        var radioButton = RadioButton(this);
        radioButton.text = plan
        radioButton.isChecked = isCheck
        planRadioGroup.addView(radioButton, index)
    }

    override fun onStop() {
//        planRadioGroup.removeAllViews()
        super.onStop()
    }
    fun makePaymentUsingPaytm(amount: Int) {
        val okHttpClient = OkHttpClient()
        val jsonValue = JSONObject()
        try {
            Log.d(TAG, "onCreate: " + intent.getIntExtra("originalprice", 0))
            jsonValue.put("TXN_AMOUNT", amount)
            //if you make the authentication than also send cust Id to server
            val custID: String = sharedPreferences.getString("member_id", "")!!
//            jsonValue.put("CUST_ID",FirebaseAuthException.)
            jsonValue.put("CUST_ID", custID)
            Log.d(TAG, "onCreate: $jsonValue")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "onCreate: $jsonValue")
        val requestBody: RequestBody = RequestBody.create(JSON, jsonValue.toString())
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
                        this@PaymentStartingActivity,
                        true,
                        true,
                        object : PaytmPaymentTransactionCallback {
                            override fun onTransactionResponse(inResponse: Bundle) {
                                Log.d(TAG, "onTransactionResponse: ${inResponse}")
                                val status = inResponse.getString("STATUS");

                                this@PaymentStartingActivity.runOnUiThread {
                                    if (status == "TXN_SUCCESS") {
                                        Toast.makeText(
                                            this@PaymentStartingActivity,
                                            "Payment successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent =
                                            Intent(
                                                this@PaymentStartingActivity,
                                                PaymentSuccessActivity::class.java
                                            )
                                        finish()
                                        intent.putExtra("inResponse", inResponse)
                                        intent.putExtra("status", 1)
                                        intent.putExtra("planName",planName)
                                        startActivity(intent)
                                    } else {

                                        Toast.makeText(
                                            this@PaymentStartingActivity,
                                            "Payment Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent =
                                            Intent(
                                                this@PaymentStartingActivity,
                                                PaymentSuccessActivity::class.java
                                            )
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
