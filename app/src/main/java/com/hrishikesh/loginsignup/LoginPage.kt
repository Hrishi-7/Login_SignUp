package com.hrishikesh.loginsignup

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        auth= FirebaseAuth.getInstance()
        sharedPref= getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        loginBtn.setOnClickListener {
            if(checking()){
                val email = usernameEt.text.toString()
                val password = passwordEt.text.toString()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task->
                    if(task.isSuccessful){
                        sharedPref.edit().putString("Email",email).apply()
                        sharedPref.edit().putBoolean("isLoggedIn",true).apply()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checking(): Boolean {
        if(usernameEt.text.toString().trim { it<= ' ' }.isNotEmpty() &&
            passwordEt.text.toString().trim { it<= ' ' }.isNotEmpty()){
            return true
        }
        return false
    }
    fun onLoginClick(view: View?){

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}