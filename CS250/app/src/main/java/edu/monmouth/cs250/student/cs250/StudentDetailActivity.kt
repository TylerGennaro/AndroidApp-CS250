package edu.monmouth.cs250.student.cs250

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide

class StudentDetailActivity : AppCompatActivity() {
    private lateinit var ctx: Context

    // companion object to create one instance of the Intent
    // save the required data as key-value using putExtra method


    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_ID = "id"
        const val EXTRA_CLASS = "class"
        const val EXTRA_DRAWABLE = "drawable"

        fun newIntent(context: Context, student: Student): Intent {
            val detailIntent = Intent(context, StudentDetailActivity::class.java)

            // force the URL to be https

            detailIntent.putExtra(EXTRA_NAME, student.name)
            detailIntent.putExtra(EXTRA_ID, student.id)
            detailIntent.putExtra(EXTRA_CLASS, student.classNum)
            detailIntent.putExtra(EXTRA_DRAWABLE, Student.getStudentImage(context, student.id))

            return detailIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        // fetch the saved data from Intent creaton above

        val name = intent?.extras?.getString(EXTRA_NAME)
        val id = intent?.extras?.getInt(EXTRA_ID)
        val classNum = intent?.extras?.getInt(EXTRA_CLASS)

        // set title for Activity
        title = name

        var studentImage: ImageView = findViewById(R.id.studentDetailImage)
        var nameTextView: TextView = findViewById(R.id.studentDetailName)
        var idTextView: TextView = findViewById(R.id.studentDetailID)
        var studentSwitch: Switch = findViewById(R.id.studentDetailSwitch)

        if (id != null) {
            Glide.with(this).load(Student.getStudentImage(this, id)).into(studentImage)
        }
        nameTextView.text = name
        idTextView.text = "s$id@monmouth.edu"

        if (classNum != null && classNum > 2) {
            studentSwitch.isChecked = true
        }
    }
}