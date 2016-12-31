package moe.lpj.kandroid.viewmodel

import android.databinding.ObservableArrayList
import kandroid.data.KCDatabase
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import moe.lpj.kandroid.utils.NotificationUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DurationFormatUtils
import java.util.*

object MissionViewModel {

    val NULL = Date() to "null"

    val missions = ObservableArrayList<Pair<Date, String>>()
    val leftTime = ObservableArrayList<String>()

    fun name(some: Pair<Date, String>): String {
        if (some !== NULL) {
            return some.second
        } else return "null"
    }

    fun time(some: Pair<Date, String>): String {
        if (some !== NULL) {
            return DateFormatUtils.format(some.first, "HH:mm:ss")
        } else return "null"
    }

    fun updateLeftTime() {
        for (i in 0..2) {
            val pair = missions[i]
            if (pair !== NULL) {
                val now = Date()
                if (pair.first.after(now)) {
                    leftTime.add(i, DurationFormatUtils.formatPeriod(now.time, pair.first.time, "HH:mm:ss"))
                } else leftTime.add(i, "00:00:00")
            } else leftTime.add(i, "null")
        }
    }

    val missionViewModelUpdater: () -> Unit = {
        val masterMissionData = KCDatabase.master.masterMissionData
        val fleets = KCDatabase.fleets

        //更新远征数据
        for (i in 0..2) {
            try {
                val fleet = fleets[i + 2]
                if (fleet != null && fleet.expeditionState != 0) {
                    missions.add(i, fleet.expeditionTime to (masterMissionData[fleet.expeditionDestination]?.name ?: "null"))
                } else {
                    missions.add(i, NULL)
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

    init {
        missions.add(NULL)
        missions.add(NULL)
        missions.add(NULL)
        api_port.port.addListener(missionViewModelUpdater)
        api_get_member.deck.addListener(missionViewModelUpdater)
    }
}