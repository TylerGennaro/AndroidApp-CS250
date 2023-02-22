package edu.monmouth.cs250.student.njgaspumps

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONException

@Parcelize

class Pump (val pumpID: Int, val name: String, val township: String, val lat: Double, val long: Double): Parcelable {
    companion object {
        fun getPumpsFromFile( filename: String,  context: Context): MutableList<Pump> {
            val pumps = mutableListOf<Pump>()

            try {
                // Load data
                val jsonString = loadJsonFromAsset(filename, context)
                if (jsonString != null) {
                    // val json = JSONObject(jsonString)  // decode JSON Sting to an key-value pair map

                    val pumpsJSONarray = JSONArray(jsonString)

                    // Get Student objects from data
                    (0 until pumpsJSONarray.length()).mapTo(pumps) {
                        Pump(
                            pumpsJSONarray.getJSONObject(it).getInt("SITE_ID"),
                            pumpsJSONarray.getJSONObject(it).getString("SITE_NAME"),
                            pumpsJSONarray.getJSONObject(it).getString("MUNICIPALITY"),
                            pumpsJSONarray.getJSONObject(it).getDouble("LATITUDE"),
                            pumpsJSONarray.getJSONObject(it).getDouble("LONGITUDE")
                        )
                    }

                } else {
                    println("not a valid JSON string")
                }


            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return pumps
        }

        // open file and read all characters into a buffer. Convert buffer to String

        private fun loadJsonFromAsset(filename: String, context: Context): String? {
            var json: String?


            try {
                val inputStream = context.assets.open(filename)
                val size = inputStream.available()
                val buffer = ByteArray(size)

                inputStream.read(buffer)
                inputStream.close()
                val charset = Charsets.UTF_8

                json = buffer.toString(charset)


            } catch (ex: java.io.IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }
    }
}