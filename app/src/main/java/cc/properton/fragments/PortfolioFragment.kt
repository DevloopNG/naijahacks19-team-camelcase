package cc.properton.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cc.properton.R
import cc.properton.adapters.PortfolioAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_portfolio.*

class PortfolioFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_portfolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        portfolio_rv.adapter = PortfolioAdapter(context!!)
    }
}