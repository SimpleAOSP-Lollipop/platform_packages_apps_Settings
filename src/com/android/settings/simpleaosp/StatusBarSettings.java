package com.android.settings.simpleaosp;

import android.os.Bundle;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class StatusBarSettings extends SettingsPreferenceFragment {

    // Statusbar general category
    private static String STATUS_BAR_GENERAL_CATEGORY = "status_bar_general_category";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.status_bar_settings);
    }
}