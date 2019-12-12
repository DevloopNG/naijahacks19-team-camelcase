package cc.properton.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cc.properton.R
import cc.properton.fragments.InnerDashboardFragment
import cc.properton.fragments.PortfolioFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.toolbar_title.text = "Dashboard"
        childFragmentManager.beginTransaction()
            .add(R.id.dashboard_container,InnerDashboardFragment(), "Dashboard").commit()

        tab_dashboard.setOnClickListener {
            switchSearchTab(it)
        }
        tab_portfolio.setOnClickListener {
            switchSearchTab(it)
        }
        tab_transactions.setOnClickListener {
            switchSearchTab(it)
        }
    }

    private fun switchSearchTab(v: View) {
        val transaction = childFragmentManager.beginTransaction()

        when (v.id) {
            R.id.tab_dashboard -> {
                transaction.replace(R.id.dashboard_container, InnerDashboardFragment(), "Dashboard")
                    .commit()
            }
            R.id.tab_portfolio -> {
                transaction.replace(R.id.dashboard_container, PortfolioFragment(), "Portfolio")
                    .commit()
            }
            R.id.tab_transactions -> {
                transaction.replace(
                    R.id.dashboard_container,
                    InnerDashboardFragment(),
                    "Transactions"
                )
                    .commit()
            }
        }
        changeActiveSearchTab(v as Button)
    }

    private fun changeActiveSearchTab(tab: Button) {
        val pureWhite = ContextCompat.getColor(context!!, R.color.pure_white)
        val inactiveBg = ContextCompat.getDrawable(context!!, R.drawable.dashboard_tab_inactive)
        val activeBg = ContextCompat.getDrawable(context!!, R.drawable.dashboard_tab_active)
        val darkText = ContextCompat.getColor(context!!, R.color.lightBlack)
        tab_dashboard.apply {
            background = inactiveBg
            setTextColor(darkText)
        }
        tab_portfolio.apply {
            background = inactiveBg
            setTextColor(darkText)
        }
        tab_transactions.apply {
            background = inactiveBg
            setTextColor(darkText)
        }
        tab.apply {
            background = activeBg
            setTextColor(pureWhite)
        }
    }
}