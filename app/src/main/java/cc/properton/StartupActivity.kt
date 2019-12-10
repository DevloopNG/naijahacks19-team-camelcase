package cc.properton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.properton.utils.PrefManager

class StartupActivity : AppCompatActivity() {
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        if (prefManager.isFirstLaunch) {
            startActivity(Intent(this@StartupActivity, OnboardingActivity::class.java))
        } else {
            startActivity(Intent(this@StartupActivity, HomeActivity::class.java))
        }
        finish()
    }
}
