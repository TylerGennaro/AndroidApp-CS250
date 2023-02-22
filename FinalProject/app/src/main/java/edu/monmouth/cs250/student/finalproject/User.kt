package edu.monmouth.cs250.student.finalproject

object User {
    var username: String = ""
    var email: String = ""
    var UID: String = ""
    var textColor: String = "Purple"

    public fun create(username: String, email: String, uid: String) {
        this.username = username
        this.email = email
        this.UID = uid
    }
}