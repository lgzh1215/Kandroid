package moe.lpj.kandroid.ui.main

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import moe.lpj.kandroid.R
import java.util.*

class HomeFragment : Fragment() {

    private var mActivity: MainActivity? = null

    private var mTabLayout: TabLayout? = null
    private var mViewPager: ViewPager? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        } else {
            throw RuntimeException(context!!.toString() + " must be MainActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        mViewPager = view.findViewById(R.id.viewpager) as ViewPager?
        mTabLayout = mActivity!!.tabLayout
        setupViewPager()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun setupViewPager() {
        val titles = ArrayList<String>()
        titles.add("Page One")

        mTabLayout!!.addTab(mTabLayout!!.newTab().setText(titles[0]))

        val fragments = ArrayList<Fragment>()
        fragments.add(HomeOverviewFragment())

        val adapter = MyAdepter(mActivity!!.supportFragmentManager, fragments, titles)
        mViewPager!!.adapter = adapter
        mTabLayout!!.setupWithViewPager(mViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    private inner class MyAdepter(fm: FragmentManager, private val mFragments: List<Fragment>, private val mTitles: List<String>) : FragmentStatePagerAdapter(fm) {

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