package moe.lpj.kandroid.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import kandroid.utils.SparseArray
import moe.lpj.kandroid.R
import moe.lpj.kandroid.application.MyApplication
import moe.lpj.kandroid.service.NotificationReceiver
import moe.lpj.kandroid.viewmodel.MissionViewModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object NotificationUtils {

    val CONTENT_TITLE = "contentTitle"
    val CONTENT_TEXT = "contentText"
    val ID = "id"

    private val log: Logger = LoggerFactory.getLogger(NotificationUtils::class.java)

    fun newTimedNotification(contentTitle: String, contentText: String, `when`: Long, id: Int) {
        val intent = Intent(MyApplication.instance, NotificationReceiver::class.java)
        intent.putExtra(CONTENT_TITLE, contentTitle)
        intent.putExtra(CONTENT_TEXT, contentText)
        intent.putExtra(ID, id)
        val requestCode = 0
        val pendingIntent = PendingIntent.getBroadcast(MyApplication.instance, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        MyApplication.instance.getAlarmManager().set(AlarmManager.RTC, `when`, pendingIntent)
        map.put(id, pendingIntent)
    }

    fun cancelTimedNotification(pendingIntent: PendingIntent) {
        MyApplication.instance.getAlarmManager().cancel(pendingIntent)
    }

    val missionNotificationId = listOf(1, 2, 3)

    val map: SparseArray<PendingIntent> = SparseArray()

    fun cancelMissionNotifications() {
        for (i in 0..2) cancelMissionNotification(i)
    }

    /**
     * @param fleetId 第二舰队 -> fleetId = 0
     */
    fun cancelMissionNotification(fleetId: Int) {
        val pendingIntent = map[missionNotificationId[fleetId]] ?: return
        cancelTimedNotification(pendingIntent)
        log.info("取消第 ${fleetId + 2} 舰队远征通知")
    }

    fun registerMissionNotifications() {
        for (i in 0..2) registerMissionNotification(i)
    }

    /**
     * @param fleetId 第二舰队 -> fleetId = 0
     */
    fun registerMissionNotification(fleetId: Int) {
        if (MissionViewModel.getInstance().missionTime[fleetId] != null) {
            newTimedNotification("第 ${fleetId + 2} 舰队远征结束",
                    "远征 ${MissionViewModel.getInstance().missionName[fleetId]} 已结束",
                    MissionViewModel.getInstance().missionTime[fleetId].time,
                    missionNotificationId[fleetId])
            log.info("注册第 ${fleetId + 2} 舰队远征通知")
        }
    }

    fun registerSimpleNotification(intent: Intent) {
        registerSimpleNotification(intent.getStringExtra(CONTENT_TITLE),
                intent.getStringExtra(CONTENT_TEXT),
                intent.getIntExtra(ID, 0))
    }

    fun registerSimpleNotification(contentTitle: String, contentText: CharSequence, id: Int) {
        val builder = MyApplication.instance.getNotificationBuilder()
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(contentTitle)
        builder.setContentText(contentText)
        builder.setShowWhen(true)
        builder.setAutoCancel(true)
        val notification = builder.build()
        MyApplication.instance.getNotificationManager().notify(id, notification)
    }
}