package com.android.settings;

import android.os.Bundle;

public class UiTweeks extends SettingsPreferenceFragment{

	@Override
	public void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.settings_ui_tweeks);
	}
	
	

}
