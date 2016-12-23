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
        } else {
            throw RuntimeException("???")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpTabLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeTabLayout()
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    private fun setUpTabLayout() {
        if (tabLayout != null) return
        val app_bar_layout = mActivity?.app_bar_layout ?: return
        val tabLayout: TabLayout = TabLayout(app_bar_layout.context)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        tabLayout.elevation = 4.0f
        app_bar_layout.addView(tabLayout)
    }

    private fun removeTabLayout() {
        mActivity!!.app_bar_layout.removeView(tabLayout)
        tabLayout = null
    }
}
