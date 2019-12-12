package cc.properton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cc.properton.utils.PrefManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var prefManager: PrefManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefManager = PrefManager(this)
        prefManager.isFirstLaunch = false

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.i("current_user_name", "Currently Signed in as:\n${currentUser.displayName}")

            val metadata = currentUser.metadata
            if (metadata?.creationTimestamp == metadata?.lastSignInTimestamp) {
                //TODO: The user is new, show them a fancy intro screen!
            } else {
                //TODO: This is an existing user, show them a welcome back screen.
            }
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings)
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)

        setSupportActionBar(home_toolbar)

        navView.setupWithNavController(navController)
        home_profile.setOnClickListener {
            val popupMenu = PopupMenu(this@MainActivity, it)
            popupMenu.inflate(R.menu.profile_popup)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.popup_view_profile -> {
                        //Start Profile Activity
                    }
                    R.id.popup_logout -> {
                        AuthUI.getInstance().signOut(this)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                true
            }
            popupMenu.show()
        }
        home_notification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
    }
}
