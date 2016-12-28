package moe.lpj.kandroid.viewmodel

import kandroid.data.KCDatabase
import moe.lpj.kandroid.activity.main.HomeMissionFragment
import moe.lpj.kandroid.utils.NotificationUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("ViewModelUtils")

val missionViewModelUpdater: () -> Unit = {
    val missionViewModel = MissionViewModel.getInstance()

    val masterMissionData = KCDatabase.master.masterMissionData
    val fleets = KCDatabase.fleets

    //更新第二舰队远征数据
    try {
        val fleet2 = fleets[2]
        val isMission2Begin = fleet2.expeditionState == 1
        if (isMission2Begin) {
            missionViewModel.mission2Name = masterMissionData[fleet2.expeditionDestination].name
            missionViewModel.mission2Time = fleet2.expeditionTime
        }
        missionViewModel.isMission2Begin = isMission2Begin
        log.info("更新第二舰队远征数据")
    } catch (e: Exception) {
        log.error("更新ViewModel失败：第二舰队远征数据 -> $e", e)
    }

    //第三
    try {
        val fleet3 = fleets[3]
        val isMission3Begin = fleet3.expeditionState == 1
        if (isMission3Begin) {
            missionViewModel.mission3Name = masterMissionData[fleet3.expeditionDestination].name
            missionViewModel.mission3Time = fleet3.expeditionTime
        }
        missionViewModel.isMission3Begin = isMission3Begin
        log.info("更新第三舰队远征数据")
    } catch (e: Exception) {
        log.error("更新ViewModel失败：第三舰队远征数据 -> $e", e)
    }

    //第四
    try {
        val fleet4 = fleets[4]
        val isMission4Begin = fleet4.expeditionState == 1
        if (isMission4Begin) {
            missionViewModel.mission4Name = masterMissionData[fleet4.expeditionDestination].name
            missionViewModel.mission4Time = fleet4.expeditionTime
        }
        missionViewModel.isMission4Begin = isMission4Begin
        log.info("更新第四舰队远征数据")
    } catch (e: Exception) {
        log.error("更新ViewModel失败：第四舰队远征数据 -> $e", e)
    }

    //远征通知
    NotificationUtils.registerMissionNotifications()
}

val viewUpdater: Runnable = Runnable {
    if (HomeMissionFragment.isVisible) {
        MissionViewModel.getInstance().notifyChange()
//        log.info("MissionView Updated!")
    }
}