package cc.properton.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cc.properton.R
import cc.properton.adapters.PropertyAdapter
import kotlinx.android.synthetic.main.layout_co_owning.*

class CoowningFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_co_owning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        co_properties_rv.adapter = PropertyAdapter(context!!)
    }
}