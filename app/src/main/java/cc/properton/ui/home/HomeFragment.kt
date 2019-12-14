package cc.properton.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cc.properton.R
import cc.properton.adapters.PropertyAdapter
import cc.properton.fragments.CoowningFragment
import cc.properton.fragments.InvestmentFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.toolbar_title.text = "Opportunities"
        childFragmentManager.beginTransaction()
                .replace(R.id.home_container, CoowningFragment(), "Co-Owning")
                .commit()

        tab_co_owning.setOnClickListener {
            switchSearchTab(it)
        }
        tab_investments.setOnClickListener {
            switchSearchTab(it)
        }
    }

    private fun switchSearchTab(v: View) {
        val transaction = childFragmentManager.beginTransaction()

        when (v.id) {
            R.id.tab_co_owning -> {
                transaction.replace(R.id.home_container, CoowningFragment(), "Co-Owning")
                        .commit()
            }
            R.id.tab_investments -> {
                transaction.replace(R.id.home_container, InvestmentFragment(), "Investment")
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
        tab_co_owning.apply {
            background = inactiveBg
            setTextColor(darkText)
        }
        tab_investments.apply {
            background = inactiveBg
            setTextColor(darkText)
        }
        tab.apply {
            background = activeBg
            setTextColor(pureWhite)
        }
    }
}