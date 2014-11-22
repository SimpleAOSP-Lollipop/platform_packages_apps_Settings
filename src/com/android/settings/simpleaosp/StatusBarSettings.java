
package com.android.settings.simpleaosp;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // General
    private static String STATUS_BAR_GENERAL_CATEGORY = "status_bar_general_category";
    // Native battery percentage
    private static final String STATUS_BAR_NATIVE_BATTERY_PERCENTAGE = "status_bar_native_battery_percentage";
    // Double-tap to sleep
    private static final String DOUBLE_TAP_SLEEP_GESTURE = "double_tap_sleep_gesture";
    private static final String STATUS_BAR_BRIGHTNESS_CONTROL = "status_bar_brightness_control";

    // General
    private PreferenceCategory mStatusBarGeneralCategory;
    // Native battery percentage
    private SwitchPreference mStatusBarNativeBatteryPercentage;
    // Double-tap to sleep
    private CheckBoxPreference mStatusBarDoubleTapSleepGesture;
    private CheckBoxPreference mStatusBarBrightnessControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.status_bar_settings);
	PreferenceScreen prefSet = getPreferenceScreen();

        // General category
        mStatusBarGeneralCategory = (PreferenceCategory) findPreference(STATUS_BAR_GENERAL_CATEGORY);

 	// Status bar brightness control
        mStatusBarBrightnessControl = (CheckBoxPreference) getPreferenceScreen().findPreference(STATUS_BAR_BRIGHTNESS_CONTROL);
        mStatusBarBrightnessControl.setChecked((Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
                Settings.System.STATUS_BAR_BRIGHTNESS_CONTROL, 0) == 1));
        try {
            if (Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                mStatusBarBrightnessControl.setEnabled(false);
                mStatusBarBrightnessControl.setSummary(R.string.status_bar_toggle_info);
            }
        } catch (SettingNotFoundException e) {
        }

        // don't show status bar brightnees control on tablet
        if (Utils.isTablet(getActivity())) {
            getPreferenceScreen().removePreference(mStatusBarBrightnessControl);
        }

       // Status bar double-tap to sleep
	mStatusBarDoubleTapSleepGesture = (CheckBoxPreference) getPreferenceScreen().findPreference(DOUBLE_TAP_SLEEP_GESTURE);
	mStatusBarDoubleTapSleepGesture.setChecked((Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
	Settings.System.DOUBLE_TAP_SLEEP_GESTURE, 0) == 1));

        // Native battery percentage
        mStatusBarNativeBatteryPercentage = (SwitchPreference) prefSet.findPreference (STATUS_BAR_NATIVE_BATTERY_PERCENTAGE);
        mStatusBarNativeBatteryPercentage.setChecked((Settings.System.getInt(getActivity()
                .getApplicationContext().getContentResolver(),
                Settings.System.STATUS_BAR_NATIVE_BATTERY_PERCENTAGE, 0) == 1));
	mStatusBarNativeBatteryPercentage.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
	ContentResolver cr = getActivity().getContentResolver();
	boolean value = (Boolean) objValue;
       if (preference == mStatusBarNativeBatteryPercentage) {
            Settings.System.putInt(cr,
                    Settings.System.STATUS_BAR_NATIVE_BATTERY_PERCENTAGE, value ? 1 : 0);
            return true;
        }

        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
	boolean value;
        if (preference == mStatusBarBrightnessControl) {
            value = mStatusBarBrightnessControl.isChecked();
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_BRIGHTNESS_CONTROL, value ? 1 : 0);
            return true;
        } else if (preference == mStatusBarDoubleTapSleepGesture) {
		value = mStatusBarDoubleTapSleepGesture.isChecked();
		Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
			Settings.System.DOUBLE_TAP_SLEEP_GESTURE, value ? 1: 0);
		return true;
		}
 		return super.onPreferenceTreeClick(preferenceScreen, preference);
    	}
}

