

import android.content.Context
import org.json.JSONException
import org.json.JSONObject

// Recipe class to read a JSON file and create a list of Recipe objects


class Recipe (

    // class properties

    val title: String,
    val description: String,
    val imageUrl: String,
    val instructionUrl: String,
    val label: String)
{

    // companion object. There is only one instancce this.
    // reads json object and create an arraylist of recipe objects.
    // uses Kotlin's built in java encoder/decoder to parse JSON data from a file.

    companion object {

        fun getRecipesFromFile(filename: String, context: Context): ArrayList<Recipe> {
            val recipeList = ArrayList<Recipe>()

            try {
                // Load data
                val jsonString = loadJsonFromAsset(filename, context)
                if (jsonString != null) {
                    val json = JSONObject(jsonString)  // decode JSON Sting to an key-value pair map
                    val recipes = json.getJSONArray("recipes")

                    // Get Recipe objects from data
                    (0 until recipes.length()).mapTo(recipeList) {
                        Recipe(recipes.getJSONObject(it).getString("title"),
                            recipes.getJSONObject(it).getString("description"),
                            recipes.getJSONObject(it).getString("image"),
                            recipes.getJSONObject(it).getString("url"),
                            recipes.getJSONObject(it).getString("dietLabel")) }
                } else {
                    println ("not a valid JSON string")
                }



            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return recipeList
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
    }
}