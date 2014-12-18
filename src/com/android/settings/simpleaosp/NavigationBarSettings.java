
package com.android.settings.simpleaosp;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class NavigationBarSettings extends SettingsPreferenceFragment implements
OnPreferenceChangeListener {

    // General
    private static String NAVIGATION_BAR_GENERAL_CATEGORY = "navigation_bar_general_category";
    // Navigation bar height
    private static final String KEY_NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    private static final String KEY_CLEAR_ALL_RECENTS_NAVBAR_ENABLED = "clear_all_recents_navbar_enabled";

    private ListPreference mNavigationBarHeight;
    private SwitchPreference mClearAllRecentsNavbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.navigation_bar_settings);
	PreferenceScreen prefs = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mNavigationBarHeight = (ListPreference) findPreference(KEY_NAVIGATION_BAR_HEIGHT);
        mNavigationBarHeight.setOnPreferenceChangeListener(this);
        int statusNavigationBarHeight = Settings.System.getInt(getActivity().getApplicationContext()
                .getContentResolver(),
                Settings.System.NAVIGATION_BAR_HEIGHT, 48);
        mNavigationBarHeight.setValue(String.valueOf(statusNavigationBarHeight));
        mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntry());

	mClearAllRecentsNavbar = (SwitchPreference) prefs.findPreference(KEY_CLEAR_ALL_RECENTS_NAVBAR_ENABLED);
        mClearAllRecentsNavbar.setChecked(Settings.System.getInt(resolver,
                    Settings.System.CLEAR_ALL_RECENTS_NAVBAR_ENABLED, 1) == 1);

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mNavigationBarHeight) {
            int statusNavigationBarHeight = Integer.valueOf((String) objValue);
            int index = mNavigationBarHeight.findIndexOfValue((String) objValue);
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.NAVIGATION_BAR_HEIGHT, statusNavigationBarHeight);
            mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntries()[index]);
        }
        return true;
    }

   @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mClearAllRecentsNavbar) {
            Settings.System.putInt(resolver, Settings.System.CLEAR_ALL_RECENTS_NAVBAR_ENABLED,
                    mClearAllRecentsNavbar.isChecked() ? 1 : 0);
        } else {
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
        return true;
    }
}
