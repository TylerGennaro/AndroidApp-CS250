package edu.monmouth.cs250.student.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
    }

    public fun register(view: View) {
        val email: String = findViewById<EditText>(R.id.input_email).text.toString()
        val username: String = findViewById<EditText>(R.id.input_username).text.toString()
        val password: String = findViewById<EditText>(R.id.input_password).text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    Log.i("Registration", "Registered new email: $email with ${task.result.user!!.uid.toString()}")
                    val uid = task.result.user!!.uid
                    val uModel = UserModel()
                    uModel.addUser(username, email)
                    User.create(username, email, uid)
                    startActivity(Intent(this, ChatsActivity::class.java))
                } else {
                    Log.i("Registration Error", "${task.exception?.message}")
                    Toast.makeText(this, "An unexpected error occurred", Toast.LENGTH_LONG).show()
                }
            }
    }
}