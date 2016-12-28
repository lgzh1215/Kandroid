package moe.lpj.kandroid.viewmodel

import kandroid.data.KCDatabase
import moe.lpj.kandroid.BR
import moe.lpj.kandroid.activity.main.HomeMissionFragment
import moe.lpj.kandroid.utils.NotificationUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("ViewModelUtils")

val missionViewModelUpdater: () -> Unit = {
    val missionViewModel = MissionViewModel.getInstance()

    val masterMissionData = KCDatabase.master.masterMissionData
    val fleets = KCDatabase.fleets

    //更新远征数据
    for (i in 0..2) {
        try {
            val fleet = fleets[i + 2]
            val isMissionBegin = fleet.expeditionState == 1
            if (isMissionBegin) {
                missionViewModel.missionName[i] = masterMissionData[fleet.expeditionDestination]?.name
                missionViewModel.missionTime[i] = fleet.expeditionTime
            } else {
                missionViewModel.missionTime[i] = null
                missionViewModel.missionName[i] = null
            }
            log.info("更新第 ${i + 2} 舰队远征数据")
        } catch (e: Exception) {
            log.error("更新ViewModel失败：第 ${i + 2} 舰队远征数据 -> $e", e)
        }
    }

    //远征通知
    NotificationUtils.cancelMissionNotifications()
    NotificationUtils.registerMissionNotifications()
}

val viewUpdater: Runnable = Runnable {
    if (HomeMissionFragment.isVisible) {
        MissionViewModel.getInstance().notifyPropertyChanged(BR.mission2LeftTime)
        MissionViewModel.getInstance().notifyPropertyChanged(BR.mission3LeftTime)
        MissionViewModel.getInstance().notifyPropertyChanged(BR.mission4LeftTime)
//        log.info("MissionView Updated!")
    }
}