package edu.monmouth.cs250.student.cs250

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.monmouth.cs250.student.cs250.StudentListCustomAdapter

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var customAdapter: StudentListCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val studentList = findViewById<RecyclerView>(R.id.studentlist_recyclerview)
        studentList.layoutManager = LinearLayoutManager(this)
        customAdapter = StudentListCustomAdapter(this, this)
        studentList.adapter = customAdapter
    }

    override fun onViewItemClicked(student: Student) {
        val detailIntent = StudentDetailActivity.newIntent(this, student)
        startActivity(detailIntent)
    }
}