package com.pinkfry.tech.Tezzo.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pinkfry.tech.Tezzo.Model.FeeModel.PaymentReminderModel
import com.pinkfry.tech.Tezzo.R
import kotlinx.android.synthetic.main.activity_payment_due.*
import kotlinx.android.synthetic.main.content_main.*

class PaymentDueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_due)
        var sharedPreferences=getSharedPreferences(resources.getString(R.string.packageName),
            Context.MODE_PRIVATE)
        var dbref= FirebaseDatabase.getInstance().reference
        dbref.child("REMINDERS").child(sharedPreferences.getString("member_id","")!!).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var paymentModel: PaymentReminderModel?=snapshot.getValue(PaymentReminderModel::class.java)
                tvNextDate.setText("Payment Due     :     ${paymentModel!!.next_due}")
                tvName.setText("Name     :     ${paymentModel!!.name}")
            }
        })
    }
}
