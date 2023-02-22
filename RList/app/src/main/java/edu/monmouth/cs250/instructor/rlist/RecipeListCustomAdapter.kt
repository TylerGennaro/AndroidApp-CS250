package edu.monmouth.cs250.instructor.rlist

import Recipe
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recipeitem_view.view.*

class RecipeListCustomAdapter ( private val context: Context, val itemviewListener: OnItemClickListner) : RecyclerView.Adapter <CustomViewHolder> () {

    private var recipeList = ArrayList<Recipe> ()

    // intializer method - get the data from Recipe model class
    // Note - we are invoking the companion object using class reference Recipe.getRecipesFromFrom(...)

    init {
        recipeList = Recipe.getRecipesFromFile("recipes.json", context)
    }

    // number of items in RecyclerView

    override fun getItemCount(): Int {
        // return students.count()
        return recipeList.count()
    }

    // create a viewHolder for the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val recipeItemLayout = LayoutInflater.from(parent.context)
        val recipeItemView = recipeItemLayout.inflate(R.layout.recipeitem_view, parent, false)
        return CustomViewHolder(recipeItemView)
    }

    // get the data for viewHolder for CustomViewHolder

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {


        val recipe = recipeList.get(position)
        holder.bind(recipe, context, itemviewListener)

    }

}
// setup the data viewHolder view.
// Image loaded using Glide image library
// Also the clickListner.

class CustomViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {

    // variables to access the views in Activity Adapter
    var titleTextView: TextView = itemView.recipeNameView
    var descTextView: TextView = itemView.recipeDescriptionView
    var recipeImage: ImageView = itemView.recipeImageView

    // bind data to view
    // Also onClickListner as a lambda
    
    fun bind (recipe: Recipe, context: Context, itemviewListner: OnItemClickListner) {

        titleTextView.text = recipe.title
        descTextView.text = recipe.description
        val imgURL = recipe.imageUrl.replace("http:", "https:", true)
        Glide.with(context).load(imgURL).into(recipeImage)

        itemView.setOnClickListener {
            itemviewListner.onViewItemClicked(recipe)
        }
    }
}

// interface spec to send callback a method in MainActivity which implements this interface

interface OnItemClickListner {
    fun onViewItemClicked(recipe: Recipe)
}

