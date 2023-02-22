package edu.monmouth.cs250.student.cs250

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StudentListCustomAdapter (private val context: Context, val itemViewListener: OnItemClickListener) : RecyclerView.Adapter <CustomViewHolder> () {

    private var studentList = ArrayList<Student> ()

    // intializer method - get the data from Recipe model class
    // Note - we are invoking the companion object using class reference Recipe.getRecipesFromFrom(...)

    init {
        studentList = Student.getStudentsFromFile("cs250students.json", context)
        println("The student list: ")
        println(studentList)
    }

    // number of items in RecyclerView

    override fun getItemCount(): Int {
        // return students.count()
        return studentList.count()
    }

    // create a viewHolder for the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val studentItemLayout = LayoutInflater.from(parent.context)
        val studentItemView = studentItemLayout.inflate(R.layout.studentlist_view, parent, false)
        return CustomViewHolder(studentItemView)
    }

    // get the data for viewHolder for CustomViewHolder

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val student = studentList[position]
        holder.bind(student, context, itemViewListener)
    }

}
// setup the data viewHolder view.
// Image loaded using Glide image library
// Also the clickListener.

class CustomViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {

    // variables to access the views in Activity Adapter
    var nameTextView: TextView = itemView.findViewById(R.id.studentNameView)
    var advTextView: TextView = itemView.findViewById(R.id.studentAdvisorView)
    var studentImage: ImageView = itemView.findViewById(R.id.studentImageView)
    var studentClass: TextView = itemView.findViewById(R.id.studentClassView)

    // bind data to view
    // Also onClickListner as a lambda

    fun bind (student: Student, context: Context, itemViewListener: OnItemClickListener) {

        nameTextView.text = student.name
        advTextView.text = student.advisor
        studentClass.text = Student.getStudentClass(student.classNum)
        Glide.with(context).load(Student.getStudentImage(context, student.id)).into(studentImage)

        itemView.setOnClickListener {
            itemViewListener.onViewItemClicked(student)
        }
    }
}

// interface spec to send callback a method in MainActivity which implements this interface

interface OnItemClickListener {
    fun onViewItemClicked(student: Student)
}