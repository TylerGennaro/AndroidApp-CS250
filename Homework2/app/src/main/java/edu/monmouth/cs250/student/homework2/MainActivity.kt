package edu.monmouth.cs250.student.homework2

import Recipe
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.monmouth.cs250.student.homework2.RecipeDetailActivity
import edu.monmouth.cs250.student.homework2.RecipeListCustomAdapter

// MainActivity.
// Also implements OnItemClickListner interface for handling selection of a recipe view


class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var customAdapter: RecipeListCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Yummy Reciepes"

        // recepie list recyclerview instantiate with linear layout.
        // instantiate a CustomAdapter (see RecipeListCustomAdapter.kt Class)
        // Setup the adapter for recyclerView

        val recipelist_recyclerview = findViewById(R.id.recipelist_recyclerview) as RecyclerView
        recipelist_recyclerview.layoutManager = LinearLayoutManager(this)
        customAdapter = RecipeListCustomAdapter(this, this)
        recipelist_recyclerview.adapter = customAdapter

    }

    // method to handle recipe view selection
    // create an intent class object for DetailActivity
    // start the activity

    override fun onViewItemClicked(recipe: Recipe) {
        val detailIntent = RecipeDetailActivity.newIntent(this, recipe)
        startActivity(detailIntent)
    }


}
