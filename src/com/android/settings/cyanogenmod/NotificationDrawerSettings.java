/*
 * Copyright (C) 2015 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.cyanogenmod;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.Preference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.cyanogenmod.qs.QSTiles;

    public class NotificationDrawerSettings extends SettingsPreferenceFragment
		implements OnPreferenceChangeListener  {

    private static final String QS_SCREENTIMEOUT_MODE = "qs_expanded_screentimeout_mode";

    private Preference mQSTiles;
    private ListPreference mScreenTimeoutMode;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.notification_drawer_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mQSTiles = findPreference("qs_order");

	// Screen timeout mode
        mScreenTimeoutMode = (ListPreference) prefSet.findPreference(QS_SCREENTIMEOUT_MODE);
        mScreenTimeoutMode.setSummary(mScreenTimeoutMode.getEntry());
        mScreenTimeoutMode.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        int qsTileCount = QSTiles.determineTileCount(getActivity());
        mQSTiles.setSummary(getResources().getQuantityString(R.plurals.qs_tiles_summary,
                    qsTileCount, qsTileCount));
    }

   @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mScreenTimeoutMode) {
            int value = Integer.valueOf((String) newValue);
            int index = mScreenTimeoutMode.findIndexOfValue((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.QS_EXPANDED_SCREENTIMEOUT_MODE, value);
            mScreenTimeoutMode.setSummary(mScreenTimeoutMode.getEntries()[index]);
            return true;
        }
        return false;
     }
}

