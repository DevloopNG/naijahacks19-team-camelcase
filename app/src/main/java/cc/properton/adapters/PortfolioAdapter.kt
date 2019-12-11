package cc.properton.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cc.properton.R
import kotlinx.android.synthetic.main.item_portfolio.view.*

class PortfolioAdapter(private val context: Context) :
    RecyclerView.Adapter<PortfolioAdapter.PortfolioHolder>() {

    inner class PortfolioHolder(val iv: View) : RecyclerView.ViewHolder(iv) {
        fun bind() {
            iv.item_portfolio_title.text = "75%"
            iv.item_portfolio_date.text = "Nov 8,2019"
            iv.item_portfolio_title.text = "2 wing duplex"
            iv.item_portfolio_status.text = "Active"
            iv.item_portfolio_image.setImageResource(R.drawable.ic_account_circle_black_24dp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioHolder =
        PortfolioHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_portfolio,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = 10
    override fun onBindViewHolder(holder: PortfolioHolder, position: Int) {
        holder.bind()
    }
}