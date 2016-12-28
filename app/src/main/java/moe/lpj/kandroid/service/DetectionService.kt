package moe.lpj.kandroid.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class DetectionService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType === AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val foregroundPackageName = event.packageName.toString()
            val foregroundClassName = event.className.toString()
            log.info(foregroundPackageName)
            log.info(foregroundClassName)
        }
    }

    override fun onInterrupt() {}

    companion object {
        val log: Logger = LoggerFactory.getLogger(DetectionService::class.java)

        fun isAccessibilitySettingsOn(context: Context): Boolean {
            var accessibilityEnabled = 0
            try {
                accessibilityEnabled = Settings.Secure.getInt(context.contentResolver,
                        Settings.Secure.ACCESSIBILITY_ENABLED)
            } catch (e: Settings.SettingNotFoundException) {
                log.error("辣鸡手机", e.message)
            }

            if (accessibilityEnabled == 1) {
                val services = Settings.Secure.getString(context.contentResolver,
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
                if (services != null) {
                    return services.toLowerCase().contains(context.packageName.toLowerCase())
                }
            }
            return false
        }

        fun startAccessibilitySettings(context: Context) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
