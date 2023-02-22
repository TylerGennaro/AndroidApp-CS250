package edu.monmouth.cs250.student.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun redirectLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    public fun redirectRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}