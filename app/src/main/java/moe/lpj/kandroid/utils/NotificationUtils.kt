package moe.lpj.kandroid.utils

import moe.lpj.kandroid.R
import moe.lpj.kandroid.application.MyApplication
import moe.lpj.kandroid.viewmodel.MissionViewModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NotificationUtils {

    val log: Logger = LoggerFactory.getLogger(NotificationUtils::class.java)

    val fleet2MissionNotificationId = 1
    val fleet3MissionNotificationId = 2
    val fleet4MissionNotificationId = 3

    fun cancelMissionNotifications() {
        cancelFleet2MissionNotification()
        cancelFleet3MissionNotification()
        cancelFleet4MissionNotification()
    }

    fun cancelFleet2MissionNotification() {
        MyApplication.instance.getNotificationManager().cancel(fleet2MissionNotificationId)
        log.info("取消第二舰队远征通知")
    }

    fun cancelFleet3MissionNotification() {
        MyApplication.instance.getNotificationManager().cancel(fleet3MissionNotificationId)
        log.info("取消第三舰队远征通知")
    }

    fun cancelFleet4MissionNotification() {
        MyApplication.instance.getNotificationManager().cancel(fleet4MissionNotificationId)
        log.info("取消第四舰队远征通知")
    }

    fun registerMissionNotifications() {
        registerFleet2MissionNotification()
        registerFleet3MissionNotification()
        registerFleet4MissionNotification()
    }

    fun registerFleet2MissionNotification() {
        if (MissionViewModel.getInstance().isMission2Begin) {
            registerSimpleNotification("第二舰队远征结束",
                    "远征 ${MissionViewModel.getInstance().mission2Name} 已结束",
                    MissionViewModel.getInstance().mission2Time.time,
                    fleet2MissionNotificationId)
            log.info("注册第二舰队远征通知")
        } else {
            cancelFleet2MissionNotification()
        }
    }

    fun registerFleet3MissionNotification() {
        if (MissionViewModel.getInstance().isMission3Begin) {
            registerSimpleNotification("第三舰队远征结束",
                    "远征 ${MissionViewModel.getInstance().mission3Name} 已结束",
                    MissionViewModel.getInstance().mission3Time.time,
                    fleet3MissionNotificationId)
            log.info("注册第三舰队远征通知")
        } else {
            cancelFleet3MissionNotification()
        }
    }

    fun registerFleet4MissionNotification() {
        if (MissionViewModel.getInstance().isMission4Begin) {
            registerSimpleNotification("第四舰队远征结束",
                    "远征 ${MissionViewModel.getInstance().mission4Name} 已结束",
                    MissionViewModel.getInstance().mission4Time.time,
                    fleet4MissionNotificationId)
            log.info("注册第四舰队远征通知")
        } else {
            cancelFleet4MissionNotification()
        }
    }

    private fun registerSimpleNotification(contentTitle: String, contentText: CharSequence, `when`: Long, id: Int) {
        val builder = MyApplication.instance.getNotificationBuilder()
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(contentTitle)
        builder.setContentText(contentText)
        builder.setWhen(`when`)
        builder.setShowWhen(true)
        builder.setAutoCancel(true)
        val notification = builder.build()
        MyApplication.instance.getNotificationManager().notify(id, notification)
    }
}