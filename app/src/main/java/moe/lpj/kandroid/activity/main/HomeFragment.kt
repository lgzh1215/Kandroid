package moe.lpj.kandroid.activity.main

import android.content.Context
import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import moe.lpj.kandroid.R
import java.util.*

class HomeFragment : Fragment() {

    private var mActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
            showTabLayout()
        } else {
            throw RuntimeException("???")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val view_pager = view.findViewById(R.id.view_pager) as ViewPager
        setupViewPager(view_pager)
        return view
    }

    override fun onDetach() {
        super.onDetach()
        hideTabLayout()
        mActivity = null
    }

    private fun showTabLayout() {
        mActivity?.tab_layout?.visibility = View.VISIBLE
    }

    private fun hideTabLayout() {
        mActivity?.tab_layout?.visibility = View.GONE
    }

    private fun setupViewPager(view_pager: ViewPager) {
        val tab_layout = mActivity!!.tab_layout

        val titles = ArrayList<String>()
        titles.add("综合")
        titles.add("综合")
        titles.add("综合")
        titles.add("远征")

        tab_layout.addTab(tab_layout.newTab().setText(titles[0]))
        tab_layout.addTab(tab_layout.newTab().setText(titles[1]))
        tab_layout.addTab(tab_layout.newTab().setText(titles[2]))
        tab_layout.addTab(tab_layout.newTab().setText(titles[3]))

        val fragments = ArrayList<Fragment>()
        fragments.add(HomeOverviewFragment())
        fragments.add(HomeOverviewFragment())
        fragments.add(HomeOverviewFragment())
        fragments.add(HomeMissionFragment())

        val fragmentAdepter = MyFragmentAdepter(mActivity!!.supportFragmentManager, fragments, titles)
        view_pager.adapter = fragmentAdepter
        mActivity!!.tab_layout.setupWithViewPager(view_pager)
    }

    private inner class MyFragmentAdepter(fm: FragmentManager,
                                          private val mFragments: List<Fragment>,
                                          private val mTitles: List<String>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }
    }

}
