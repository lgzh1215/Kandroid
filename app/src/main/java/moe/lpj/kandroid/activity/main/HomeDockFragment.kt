package moe.lpj.kandroid.activity.main


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import moe.lpj.kandroid.R
import moe.lpj.kandroid.databinding.FragmentHomeDockBinding
import moe.lpj.kandroid.viewmodel.DockViewModel


class HomeDockFragment : Fragment() {

    private var mActivity: MainActivity? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val homeDockBinding = FragmentHomeDockBinding.inflate(inflater, container, false)
        homeDockBinding.dock = DockViewModel
        return homeDockBinding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context as MainActivity?
        } else {
            throw RuntimeException("???")
        }
    }

    override fun onResume() {
        super.onResume()
        co.isVisible = true
    }

    override fun onStop() {
        super.onStop()
        co.isVisible = false
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    companion object co {
        var isVisible = false
    }

}
