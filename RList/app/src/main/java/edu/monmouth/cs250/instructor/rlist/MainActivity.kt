package edu.monmouth.cs250.instructor.rlist

import Recipe
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

// MainActivity.
// Also implements OnItemClickListner interface for handling selection of a recipe view


class MainActivity : AppCompatActivity(), OnItemClickListner {

    private lateinit var customAdapter: RecipeListCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Yummy Reciepes"

        // recepie list recyclerview instantiate with linear layout.
        // instantiate a CustomAdapter (see RecipeListCustomAdapter.kt Class)
        // Setup the adapter for recyclerView

        recipelist_recyclerview.layoutManager = LinearLayoutManager (this)
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
