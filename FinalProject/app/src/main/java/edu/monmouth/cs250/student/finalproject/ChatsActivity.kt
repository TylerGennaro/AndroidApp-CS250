package edu.monmouth.cs250.student.finalproject

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.IgnoreExtraProperties
import edu.monmouth.cs250.student.finalproject.databinding.ActivityChatsBinding

@IgnoreExtraProperties
data class Message(
    val poster: String? = "",
    val posterUID: String? = "",
    val message: String? = ""
)

@IgnoreExtraProperties
data class Room(
    val creator: String? = "",
    val creatorUID: String? = "",
    val title: String? = "",
    val privacy: Int? = 0        // 0 = Private (owner invite only), 1 = Anyone in room can invite, 2 = Anyone can find/join
)

class ChatsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityChatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarChats.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_chats)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_chats, R.id.nav_discover, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerView: View = navView.getHeaderView(0)
        val navUsername: TextView = headerView.findViewById(R.id.nav_username)
        val navEmail: TextView = headerView.findViewById(R.id.nav_email)
        navUsername.text = User.username
        navEmail.text = User.email
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.chats, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_chats)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}