package edu.monmouth.cs250.student.cs250

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import org.json.JSONException
import org.json.JSONObject

class Student (
    // class properties

    val name: String,
    val id: Int,
    val email: String,
    val major: String,
    val classNum: Int,
    val advisor: String,
    val credits: Int,
    val adminStatus: String)
    {
        // companion object. There is only one instancce this.
        // reads json object and create an arraylist of recipe objects.
        // uses Kotlin's built in java encoder/decoder to parse JSON data from a file.

        companion object {

        fun getStudentsFromFile(filename: String, context: Context): ArrayList<Student> {
            val studentList = ArrayList<Student>()

            try {
                // Load data
                val jsonString = loadJsonFromAsset(filename, context)
                if (jsonString != null) {
                    val json = JSONObject(jsonString)  // decode JSON Sting to an key-value pair map
                    val students = json.getJSONArray("cs250Students")

                    // Get Recipe objects from data
                    (0 until students.length()).mapTo(studentList) {
                        Student(students.getJSONObject(it).getString("Student Name"),
                            Integer.parseInt(students.getJSONObject(it).getString("ID")),
                            students.getJSONObject(it).getString("Email"),
                            students.getJSONObject(it).getString("Major"),
                            Integer.parseInt(students.getJSONObject(it).getString("Class")),
                            students.getJSONObject(it).getString("Advisor"),
                            Integer.parseInt(students.getJSONObject(it).getString("Credits")),
                            students.getJSONObject(it).getString("Admit Status")
                        )
                    }
                } else {
                    println ("not a valid JSON string")
                }



            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return studentList
        }

        // open file and read all characters into a buffer. Convert buffer to String

        private fun loadJsonFromAsset(filename: String, context: Context): String? {
            var jsonString: String?


            try {
                val inputStream = context.assets.open(filename)
                val size = inputStream.available()
                val buffer = ByteArray(size)

                inputStream.read(buffer)
                inputStream.close()
                val charset = Charsets.UTF_8

                jsonString = buffer.toString(charset)


            } catch (ex: java.io.IOException) {
                ex.printStackTrace()
                return null
            }

            return jsonString
        }

        fun getStudentClass(classNum: Int): String? {
            val classMap = mutableMapOf<Int, String>(1 to "Freshman", 2 to "Sophomore", 3 to "Junior", 4 to "Senior")
            return classMap[classNum]
        }

        fun getStudentImage(ctx: Context, id: Int): Int {
            return ctx.resources.getIdentifier("s$id", "drawable", ctx.applicationInfo.packageName)
        }
    }
}