package moe.lpj.kandroid.viewmodel

import kandroid.data.KCDatabase
import moe.lpj.kandroid.activity.main.HomeDockFragment
import moe.lpj.kandroid.activity.main.HomeMissionFragment
import moe.lpj.kandroid.utils.NotificationUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("ViewModelUtils")

val viewUpdater: Runnable = Runnable {
    if (HomeMissionFragment.isVisible) {
        try {
            MissionViewModel.updateLeftTime()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
    if (HomeDockFragment.isVisible) {
        try {
            DockViewModel.updateLeftTime()
        }  catch(e: Exception) {
            e.printStackTrace()
        }
    }
}