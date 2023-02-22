package edu.monmouth.cs250.student.homework2

import Recipe
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

import androidx.appcompat.app.AppCompatActivity


// Activity to show the Recipe inside a webView


class RecipeDetailActivity: AppCompatActivity() {

    private lateinit var recipewebview: WebView

    // companion object to create one instance of the Intent
    // save the required data as key-value using putExtra method


    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"

        fun newIntent(context: Context, recipe: Recipe): Intent {
            val detailIntent = Intent(context, RecipeDetailActivity::class.java)

            // force the URL to be https

            val secureURL = recipe.instructionUrl.replace("http:",
                "https:", true)

            detailIntent.putExtra(EXTRA_TITLE, recipe.title)
            detailIntent.putExtra(EXTRA_URL, secureURL)

            return detailIntent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipedetail)

        // fetch the saved data from Intent creaton above

        val title = intent?.extras?.getString(EXTRA_TITLE)
        val url = intent?.extras?.getString(EXTRA_URL)

        // set title for Activity
        setTitle(title)

        // load the webview with recipe page HTML content

        recipewebview = findViewById(R.id.recipewebview)

        //do not transition to browser  - show the webview inside the app

        recipewebview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: String?
            ): Boolean {
                return false
            }
        }

        if (url != null) {
            recipewebview.loadUrl(url)
        }

    }
}