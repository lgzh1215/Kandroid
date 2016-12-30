package moe.lpj.kandroid.activity.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import moe.lpj.kandroid.R
import moe.lpj.kandroid.utils.NotificationUtils

class HomeOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_overview, container, false)
        val button = view.findViewById(R.id.button) as Button
        button.setOnClickListener {
            NotificationUtils.newTimedNotification("woiefwoe", "csioanason",
                    System.currentTimeMillis() + 5000L, 213)
        }
        return view
    }
}
