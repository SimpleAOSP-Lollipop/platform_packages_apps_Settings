package com.android.settings.simpleaosp;

import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.preference.Preference;
import com.android.settings.R;
import android.provider.Settings;
import com.android.settings.SettingsPreferenceFragment;

public class StatusBarSettings extends SettingsPreferenceFragment {

    // Statusbar general category
    private static String STATUS_BAR_GENERAL_CATEGORY = "status_bar_general_category";
    private static final String KEY_STATUS_BAR_CLOCK = "clock_style_pref";

    private PreferenceScreen mClockStyle;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.status_bar_settings);
	PreferenceScreen prefSet = getPreferenceScreen();

	mClockStyle = (PreferenceScreen) prefSet.findPreference(KEY_STATUS_BAR_CLOCK);
        updateClockStyleDescription();
    }
    @Override
    public void onResume() {
        super.onResume();
        updateClockStyleDescription();
    }

    private void updateClockStyleDescription() {
        if (mClockStyle == null) {
            return;
        }
        if (Settings.System.getInt(getContentResolver(),
               Settings.System.STATUS_BAR_CLOCK, 1) == 1) {
            mClockStyle.setSummary(getString(R.string.enabled_string));
        } else {
            mClockStyle.setSummary(getString(R.string.disabled));
         }
    }
}
