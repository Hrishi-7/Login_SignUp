package com.hrishikesh.loginsignup

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPref= getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        signUpBtn.setOnClickListener {
            if(checking()){
                val email = userEmailId.text.toString()
                val password = password.text.toString()
                val name = fullName.text.toString()
                val number = mobileNumber.text.toString()
                val user = hashMapOf(
                    "Name" to name,
                    "Phone" to number,
                    "email" to email
                )
                val Users = db.collection("USERS")
                val query = Users.whereEqualTo("email", email).get()
                    .addOnSuccessListener {
                            tasks->
                        if(tasks.isEmpty){
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) {
                                        task->
                                    if(task.isSuccessful){
                                        Users.document(email).set(user)
                                        sharedPref.edit().putBoolean("isLoggedIn",true).apply()
                                        sharedPref.edit().putString("Email",email).apply()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }else{
                            Toast.makeText(this, "Users Already Registered", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

            }else{
                Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checking(): Boolean {
        if(fullName.text.toString().trim { it<=' ' }.isNotEmpty() &&
            userEmailId.text.toString().trim { it<=' ' }.isNotEmpty() &&
            mobileNumber.text.toString().trim { it<=' ' }.isNotEmpty() &&
            password.text.toString().trim { it<=' ' }.isNotEmpty()){
            return true
        }
        return false
    }

    fun onRegisterClick(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}