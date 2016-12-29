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
import moe.lpj.kandroid.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    var mActivity: MainActivity? = null

    lateinit var binding: FragmentHomeBinding private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        } else {
            throw RuntimeException("???")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity!!.showTabLayout()
//        mActivity!!.binding.activityMainContent.toolbar.
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        mActivity!!.hideTabLayout()
        mActivity = null
    }

    private fun setupViewPager(view_pager: ViewPager) {
        val tab_layout = mActivity!!.binding.activityMainContent.tabLayout

        val titles = ArrayList<String>()
        titles.add("综合")
        titles.add("远征")

        tab_layout.addTab(tab_layout.newTab().setText(titles[0]))
        tab_layout.addTab(tab_layout.newTab().setText(titles[1]))

        val fragments = ArrayList<Fragment>()
        fragments.add(HomeOverviewFragment())
        fragments.add(HomeMissionFragment())

        val fragmentAdepter = MyFragmentAdepter(fragments, titles, activity.supportFragmentManager)
        view_pager.adapter = fragmentAdepter
        tab_layout.setupWithViewPager(view_pager)
    }

    private inner class MyFragmentAdepter(private val mFragments: List<Fragment>,
                                          private val mTitles: List<String>,
                                          fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

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
