package moe.lpj.kandroid.activity.setting

import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.*
import android.text.TextUtils
import android.view.MenuItem
import kandroid.KandroidMain
import moe.lpj.kandroid.R
import moe.lpj.kandroid.application.App
import moe.lpj.kandroid.kandroid.ConfigA
import moe.lpj.kandroid.utils.NotificationUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class SettingsActivity : AppCompatPreferenceActivity() {

    val log: Logger = LoggerFactory.getLogger(SettingsActivity::class.java)

    private object onSharedPreferenceChangeListener : SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            if (key.startsWith("notification")) {
                NotificationUtils.registerMissionNotifications()
            } else if (key.startsWith("proxy")) {
                KandroidMain.updateConfig(ConfigA.get(App.get()))
                KandroidMain.restart()
            }
        }
    }

    /**
     * A preference value change listener that updates the preference'ses summary
     * to reflect its new value.
     */
    private object sBindPreferenceSummaryToValueListener : Preference.OnPreferenceChangeListener {
        override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
            val stringValue = value.toString()

            when (preference) {
                is ListPreference -> {
                    // For list preferences, look up the correct display value in
                    // the preference'ses 'entries' list.
                    val index = preference.findIndexOfValue(stringValue)

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            if (index >= 0)
                                preference.entries[index]
                            else
                                null)
                }
                is RingtonePreference -> {
                    // For ringtone preferences, look up the correct display value
                    // using RingtoneManager.
                    if (TextUtils.isEmpty(stringValue)) {
                        // Empty values correspond to 'silent' (no ringtone).
                        preference.setSummary("Silent")

                    } else {
                        val ringtone = RingtoneManager.getRingtone(
                                preference.getContext(), Uri.parse(stringValue))

                        if (ringtone == null) {
                            // Clear the summary if there was a lookup error.
                            preference.setSummary(null)
                        } else {
                            // Set the summary to reflect the new ringtone display
                            // name.
                            val name = ringtone.getTitle(preference.getContext())
                            preference.setSummary(name)
                        }
                    }
                }
                else -> {
                    // For all other preferences, set the summary to the value'ses
                    // simple string representation.
                    preference.summary = stringValue
                }
            }
            return true
        }
    }

    companion object {
        /**
         * Binds a preference'ses summary to its value. More specifically, when the
         * preference'ses value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.
         * @see .sBindPreferenceSummaryToValueListener
         */
        private fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference'ses
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager.getDefaultSharedPreferences(preference.context).getString(preference.key, ""))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * {@inheritDoc}
     */
    override fun onIsMultiPane(): Boolean {
        return resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    /**
     * {@inheritDoc}
     */
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.pref_headers, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || ProxyPreferenceFragment::class.java.name == fragmentName
                || DebugPreferenceFragment::class.java.name == fragmentName
                || NotificationPreferenceFragment::class.java.name == fragmentName
    }

    class ProxyPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_proxy)
            setHasOptionsMenu(true)
            bindPreferenceSummaryToValue(findPreference("proxy_listenPort"))
            bindPreferenceSummaryToValue(findPreference("proxy_proxyPort"))
            bindPreferenceSummaryToValue(findPreference("proxy_proxyHost"))
        }
    }

    class DebugPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_debug)
            setHasOptionsMenu(true)
        }
    }

    class NotificationPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_notification)
            setHasOptionsMenu(true)

            bindPreferenceSummaryToValue(findPreference("notification_mission_ahead"))
        }
    }
}