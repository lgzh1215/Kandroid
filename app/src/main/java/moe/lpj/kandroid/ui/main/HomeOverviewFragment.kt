package moe.lpj.kandroid.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import moe.lpj.kandroid.R

class HomeOverviewFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        recyclerView = inflater!!.inflate(R.layout.fragment_home_overview, container, false) as RecyclerView
        return recyclerView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView!!.layoutManager = LinearLayoutManager(recyclerView!!.context)
    }
}
