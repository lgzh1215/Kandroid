package moe.lpj.kandroid.activity.main

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import moe.lpj.kandroid.R

class HomeFragment : Fragment() {

    private var mActivity: MainActivity? = null

    private var tabLayout: TabLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
            showTabLayout()
        } else {
            throw RuntimeException("???")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        hideTabLayout()
        mActivity = null
    }

    private fun showTabLayout() {
        mActivity!!.tab_layout.visibility = View.VISIBLE
    }

    private fun hideTabLayout() {
        mActivity!!.tab_layout.visibility = View.GONE
    }
}
