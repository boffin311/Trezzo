package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.annotations.NotNull
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.Model.FeeModel.PaymentAddedModel
import com.pinkfry.tech.Tezzo.R
import com.pinkfry.tech.Tezzo.RequestInterface.ApiCalls
import kotlinx.android.synthetic.main.activity_payment_success.*
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

class PaymentSuccessActivity : AppCompatActivity() {
    lateinit var customDialogProgressBar: CustomDialogeProgressBar
    val TAG = "PSA";
    val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var planName:String
    lateinit var inResponse: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE);
        customDialogProgressBar= CustomDialogeProgressBar(this);
        customDialogProgressBar.setCancelable(false)
        customDialogProgressBar.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        Log.d(TAG, "here" + intent.getBundleExtra("inResponse"))
        planName=intent.getStringExtra("planName")!!
        inResponse =intent.getBundleExtra("inResponse")!!
        if (intent.getIntExtra("status", 0) == 1)
            checkPaymentStatus(inResponse)
        else {
            linearPaymentDone.visibility = View.VISIBLE;
            tvPaymentStatus.text = "Payment Failed"
            tvPaymentStatus.setTextColor(Color.RED)
            imagePaymentStatus.setImageResource(R.drawable.payment_failed)
            val json = JSONObject()
            val keys: Set<String> = inResponse.keySet()
            for (key in keys) {
                try {
                    json.put(key, JSONObject.wrap(inResponse.get(key)))
                } catch (e: JSONException) {
                    //Handle exception here
                }
            }
            customDialogProgressBar.show()
            sendPaymentResponseToServer(json)
        }

    }

    fun sendPaymentResponseToServer( response: JSONObject){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.tezzo.fit/diet/").build()
            .baseUrl(resources.getString(R.string.baseUrlServer,"fee/")).build()
        Log.d(TAG,sharedPreferences.getString("gymId","")!!)
        Log.d(TAG,sharedPreferences.getString("member_id","")!!)
        Log.d(TAG,planName)
        Log.d(TAG,response.toString())
        retrofit.create(ApiCalls::class.java).getPaymentAddStatus(sharedPreferences.getString("gymId",""),planName,sharedPreferences.getString("member_id",""),response)
            .enqueue(object : Callback<PaymentAddedModel>{
                override fun onFailure(call: Call<PaymentAddedModel>, t: Throwable) {
                    Log.d(TAG,"failed ")
                 customDialogProgressBar.dismiss()
                }

                override fun onResponse(
                    call: Call<PaymentAddedModel>,
                    response: Response<PaymentAddedModel>
                ) {
                    Log.d(TAG,"success  ${response.body()}")
                    customDialogProgressBar.dismiss()
                }
            })
    }
    fun checkPaymentStatus(inResponse: Bundle) {
        val okHttpClient = OkHttpClient();
        val ORDERID = inResponse.getString("ORDERID")
        Log.d(TAG, "onTransactionResponse: $ORDERID")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("ORDERID", ORDERID)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody: RequestBody =
            RequestBody.create(JSON, jsonObject.toString())
        val request: Request = Request.Builder()
//            .url("https://checksum-generator-pinkfry.herokuapp.com/gettxnstatus")
            .url(resources.getString(R.string.paymentBaseUrl,"gettxnstatus"))
            .post(requestBody)
            .build()
        okHttpClient.newCall(request)
            .enqueue(object : okhttp3.Callback {
                override fun onFailure(
                    @NotNull call: okhttp3.Call,
                    @NotNull e: IOException?
                ) {
                    Log.d(TAG, "onFailure: ")
//                    Toast.makeText(this@PaymentSuccessActivity,"Something went Wrong,check your ",Toast.LENGTH_SHORT).show()
                    imagePaymentStatus.setImageResource(R.drawable.no_internet_modified)
                    tvPaymentStatus.text =
                        "Something Went wrong, Try connecting to internet and try again"
                    this@PaymentSuccessActivity.runOnUiThread { gifProgress.visibility = View.GONE }
                }

                @Throws(IOException::class)
                override fun onResponse(
                    @NotNull call: okhttp3.Call,
                    @NotNull response: okhttp3.Response
                ) {
                    this@PaymentSuccessActivity.runOnUiThread { gifProgress.visibility = View.GONE }
                    try {
                        val jsonobjectFinalResult =
                            JSONObject(response.body()!!.string())
                        Log.d(TAG, "onRes: $jsonobjectFinalResult");
                        val finalStatus: String =
                            jsonobjectFinalResult.getString("STATUS")
                        Log.d(
                            TAG,
                            "onResponse: $finalStatus"
                        )
                        when (finalStatus) {
                            "TXN_SUCCESS" -> {
                                this@PaymentSuccessActivity.runOnUiThread {
                                    linearPaymentDone.visibility = View.VISIBLE
                                    imagePaymentStatus.setImageResource(R.drawable.payment_successful)
                                    tvPaymentStatus.text = "Payment Successful"
                                    tvPaymentStatus.setTextColor(resources.getColor(R.color.result_points))
                                    customDialogProgressBar.show()

                                    sendPaymentResponseToServer(jsonobjectFinalResult)
                                }


                            }
                            "TXN_FAILURE" -> {
                                this@PaymentSuccessActivity.runOnUiThread {
                                    linearPaymentDone.visibility = View.VISIBLE
//                                    imagePaymentStatus.setImageResource(R.drawable.payment_successful)
                                    tvPaymentStatus.text = "Payment Processing"
                                    tvPaymentStatus.setTextColor(Color.GRAY)
                                    customDialogProgressBar.show()
                                    sendPaymentResponseToServer(jsonobjectFinalResult)
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
    }
}
