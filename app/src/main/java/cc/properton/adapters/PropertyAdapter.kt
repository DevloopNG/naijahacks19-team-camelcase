package cc.properton.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cc.properton.DetailsActivity
import cc.properton.R
import kotlinx.android.synthetic.main.item_property.view.*

class PropertyAdapter(private val context: Context) :
    RecyclerView.Adapter<PropertyAdapter.PropertyHolder>() {

    inner class PropertyHolder(val iv: View) : RecyclerView.ViewHolder(iv) {
        fun bind() {
            iv.item_percentage_raised.text = "75%"
            iv.item_property_time_left.text = "6 days left"
            iv.item_property_title.text = "2 wing duplex"
            iv.item_property_location.text = "Ajah, Lagos"
            iv.item_property_image.setImageResource(R.drawable.ic_account_circle_black_24dp)
            iv.setOnClickListener {
                context.startActivity(Intent(context, DetailsActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyHolder =
        PropertyHolder(LayoutInflater.from(context).inflate(R.layout.item_property, parent, false))

    override fun getItemCount(): Int = 10
    override fun onBindViewHolder(holder: PropertyHolder, position: Int) {
        holder.bind()
    }
}