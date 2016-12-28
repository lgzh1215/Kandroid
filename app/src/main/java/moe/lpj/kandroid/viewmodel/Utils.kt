package moe.lpj.kandroid.viewmodel

import kandroid.data.KCDatabase
import moe.lpj.kandroid.activity.main.HomeMissionFragment

val missionViewModelUpdater: () -> Unit = {
    val missionViewModel = MissionViewModel.Holder.Instance

    val masterMissionData = KCDatabase.master.masterMissionData
    val fleets = KCDatabase.fleets

    //更新第二舰队远征数据
    val fleet2 = fleets[2]
    val isMission2Begin = fleet2.expeditionState == 1
    if (isMission2Begin) {
        missionViewModel.mission2Name = masterMissionData.get(fleet2.expeditionDestination).name
        missionViewModel.mission2Time = fleet2.expeditionTime
    }
    missionViewModel.isMission2Begin = isMission2Begin

    //第三
    val fleet3 = fleets[3]
    val isMission3Begin = fleet3.expeditionState == 1
    if (isMission3Begin) {
        missionViewModel.mission3Name = masterMissionData.get(fleet3.expeditionDestination).name
        missionViewModel.mission3Time = fleet3.expeditionTime
    }
    missionViewModel.isMission3Begin = isMission3Begin

    //第四
    val fleet4 = fleets[4]
    val isMission4Begin = fleet4.expeditionState == 1
    if (isMission4Begin) {
        missionViewModel.mission4Name = masterMissionData.get(fleet4.expeditionDestination).name
        missionViewModel.mission4Time = fleet4.expeditionTime
    }
    missionViewModel.isMission4Begin = isMission4Begin

    //远征通知应对
}

val viewUpdater: Runnable = Runnable {
    if (HomeMissionFragment.isVisible)
        MissionViewModel.Holder.Instance.notifyChange()
}