package com.hrishikesh.loginsignup

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)!!
        val isLogin = sharedPref.getBoolean("isLoggedIn", false)

        logoutBtn.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            sharedPref.edit().remove("isLoggedIn").apply()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            this.finish()
            Log.e("z", "logoutBtn")
        }

        if(isLogin){
            val email =  sharedPref.getString("Email",null)
            if(email!=null){

            }else{
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                Toast.makeText(this,"ToastOne", Toast.LENGTH_LONG).show()
                this.finish()
            }
        }else{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            Toast.makeText(this,"ToastTwo", Toast.LENGTH_LONG).show()
            this.finish()
        }
    }
}