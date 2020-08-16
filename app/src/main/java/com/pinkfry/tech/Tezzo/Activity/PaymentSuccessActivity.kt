package com.pinkfry.tech.Tezzo.Activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.annotations.NotNull
import com.pinkfry.tech.Tezzo.Dialogue.CustomDialogeProgressBar
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.activity_payment_success.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PaymentSuccessActivity : AppCompatActivity() {
    lateinit var customDialogProgressBar:CustomDialogeProgressBar
val TAG="PSA";
    val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        Log.d(TAG,"here" +intent.getBundleExtra("inResponse"))
        if(intent.getIntExtra("status",0)==1)
             checkPaymentStatus(intent.getBundleExtra("inResponse")!!)
        else {
            linearPaymentDone.visibility=View.VISIBLE;
            tvPaymentStatus.text = "Payment Failed"
            tvPaymentStatus.setTextColor(Color.RED)
        }

    }


    fun checkPaymentStatus(inResponse:Bundle){
        val okHttpClient=OkHttpClient();
        val ORDERID = inResponse.getString("ORDERID")
        Log.d(TAG, "onTransactionResponse: $ORDERID")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("ORDERID", ORDERID)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody: RequestBody =
            RequestBody.create(JSON,jsonObject.toString())
        val request: Request = Request.Builder()
            .url("https://checksum-generator-pinkfry.herokuapp.com/gettxnstatus")
            .post(requestBody)
            .build()
        okHttpClient.newCall(request)
            .enqueue(object : okhttp3.Callback {
                override fun onFailure(
                    @NotNull call: okhttp3.Call,
                    @NotNull e: IOException?
                ) {
                    Log.d(TAG, "onFailure: ")
                    Toast.makeText(this@PaymentSuccessActivity,"Failed response",Toast.LENGTH_SHORT).show()
                    this@PaymentSuccessActivity.runOnUiThread{ gifProgress.visibility=View.GONE}
                }

                @Throws(IOException::class)
                override fun onResponse(
                    @NotNull call: okhttp3.Call,
                    @NotNull response: okhttp3.Response
                ) {
                    this@PaymentSuccessActivity.runOnUiThread{ gifProgress.visibility=View.GONE}
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
                                    linearPaymentDone.visibility= View.VISIBLE
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
