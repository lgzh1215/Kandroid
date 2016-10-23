package moe.lpj.kandroid.ui.main


import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import moe.lpj.kandroid.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

}
