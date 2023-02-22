package edu.monmouth.cs250.student.finalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
    }

    override fun onResume() {
        super.onResume()

        Log.i("Login", "Trying")
        auth.signInWithEmailAndPassword("test@example.com", "Test123")
            .addOnCompleteListener { task ->
                Log.i("Login", "Complete")
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    Log.i ("Login", task.result.user!!.uid)
                    val uModel = UserModel()
                    var uid = task.result.user!!.uid
                    User.create("TGennaro", "tgjaguar@gmail.com", uid)
                    startActivity(Intent(this, ChatsActivity::class.java))
                } else {
                    Toast.makeText(this, "Unable to login. Check your input or try again later", Toast.LENGTH_SHORT).show()
                }
            }
    }

    public fun login(view: View) {
        val username: String = findViewById<EditText>(R.id.input_username).text.toString()
        val password: String = findViewById<EditText>(R.id.input_password).text.toString()

        var email: String? = null
        db = Firebase.database.reference
        Log.i("Login", "Sending Firebase request")
        db.child("Users").child(username).get().addOnSuccessListener {
            email = it.value as String
            Log.i("Login", "Found email $email")

            if (email == null) {
                Toast.makeText(this, "Username not registered", Toast.LENGTH_LONG).show()
            } else {
                Log.i("Login", "Sending Auth request")
                auth.signInWithEmailAndPassword(email!!, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            Log.i ("Login", task.result.user!!.uid)
                            val uModel = UserModel()
                            var uid = task.result.user!!.uid
                            User.create(username, email!!, uid)
                            startActivity(Intent(this, ChatsActivity::class.java))
                        } else {
                            Toast.makeText(this, "Unable to login. Check your input or try again later", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}