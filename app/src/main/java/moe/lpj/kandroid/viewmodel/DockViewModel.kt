package moe.lpj.kandroid.viewmodel

import android.databinding.ObservableArrayList
import kandroid.data.DockData
import kandroid.data.KCDatabase
import kandroid.data.ShipData
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DurationFormatUtils
import java.util.*


object DockViewModel {

    val NULL = 0 to Date()

    val enable = ObservableArrayList<Boolean>()
    val docks = ObservableArrayList<Pair<Int, Date>>()
    val leftTime = ObservableArrayList<String>()

    fun name(some: Pair<Int, Date>): String {
        if (some !== NULL) {
            val ship: ShipData? = KCDatabase.ships[some.first]
            if (ship == null) return "正体不明"
            else return ship.masterShip.name
        }
        return "未使用"
    }

    fun time(some: Pair<Int, Date>): String {
        if (some !== NULL) {
            return DateFormatUtils.format(some.second, "HH:mm:ss")
        }
        return ""
    }

    fun updateLeftTime() {
        for (i in 0..2) {
            val pair = docks[i]
            if (pair !== NULL) {
                val now = Date()
                if (pair.second.after(now)) {
                    leftTime.add(i, DurationFormatUtils.formatPeriod(now.time, pair.second.time, "HH:mm:ss"))
                } else leftTime.add(i, "00:00:00")
            } else leftTime.add(i, "")
        }
    }

    val dockViewModelUpdater: () -> Unit = {

        for (i in 0..3) {
            try {
                val dock: DockData? = KCDatabase.docks[i + 1]
                if (dock != null) {
                    val state = dock.state
                    when (state) {
                        -1 -> {
                            enable[i] = false
                            docks.add(i, NULL)
                        }
                        0 -> {
                            enable[i] = true
                            docks.add(i, NULL)
                        }
                        1 -> {
                            enable[i] = true
                            docks.add(i, dock.shipId to dock.completionTime)
                        }
                    }
                    log.info("更新第 ${i + 1} 入渠数据")
                }
            } catch (e: Exception) {
                log.error("更新ViewModel失败：第 ${i + 1} 入渠数据 -> $e", e)
            }
        }
    }

    init {
        enable.add(true)
        enable.add(true)
        enable.add(false)
        enable.add(false)
        docks.add(NULL)
        docks.add(NULL)
        docks.add(NULL)
        docks.add(NULL)
        api_port.port.addListener(dockViewModelUpdater)
        api_get_member.ndock.addListener(dockViewModelUpdater)
    }
}