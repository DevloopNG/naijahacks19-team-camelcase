package cc.properton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private var activeTab: Int = R.id.btnDirectBankPayment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

    }

    fun switchSearchTab(view: View) {
        activeTab = view.id

        when (activeTab) {
            R.id.btnDirectBankPayment -> {
                layoutDirectPayment.visibility = View.VISIBLE
                layoutWireTransfer.visibility = View.GONE
                layoutNHS.visibility = View.GONE
            }
            R.id.btnWireTransfer -> {
                layoutWireTransfer.visibility = View.VISIBLE
                layoutDirectPayment.visibility = View.GONE
                layoutNHS.visibility = View.GONE
            }
            R.id.btnNHS -> {
                layoutNHS.visibility = View.VISIBLE
                layoutWireTransfer.visibility = View.GONE
                layoutDirectPayment.visibility = View.GONE
            }
        }
    }
}
