package edu.monmouth.cs250.student.finalproject

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserModel {
    private lateinit var db: DatabaseReference

    fun addUser(username: String, email: String) {
        db = Firebase.database.reference
        db.child("Users").child(username).setValue(email)
    }
}