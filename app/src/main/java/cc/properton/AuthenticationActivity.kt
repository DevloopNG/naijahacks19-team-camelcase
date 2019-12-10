package cc.properton

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        startSignup()
    }

    private fun startSignup() {
        if (auth.currentUser != null) {
            startActivity(Intent(this@AuthenticationActivity, NotificationActivity::class.java))
        } else {
            val authMethodPickerLayout =
                AuthMethodPickerLayout.Builder(R.layout.layout_signup)
                    .setPhoneButtonId(R.id.phone_login_btn)
                    .setTosAndPrivacyPolicyId(R.id.privacy_policy_txt)
                    .build()
            val phoneAuth = AuthUI.IdpConfig.PhoneBuilder()
                .setDefaultCountryIso("ng")
                .build()

            val authSignupIntent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .setTosAndPrivacyPolicyUrls(
                    "http://properton.xyz",
                    "http://properton.xyz/privacypolicy"
                )
                .setAvailableProviders(arrayListOf(phoneAuth))
                .build()
            startActivityForResult(authSignupIntent, RC_SIGN_IN)
        }
    }
}