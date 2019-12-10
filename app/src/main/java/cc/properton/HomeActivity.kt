package cc.properton

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cc.properton.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var prefManager: PrefManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings)
        )
        setSupportActionBar(home_toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        logout_btn.setOnClickListener {
//            AuthUI.getInstance().signOut(this)
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
    }
}
