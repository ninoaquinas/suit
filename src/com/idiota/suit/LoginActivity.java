package com.idiota.suit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "U3cwotKUuI2OSBsfzITZCX9EQwW2wpjHJeRfkgDI", 
						 "83F9laWZ1ESTJJLFyr3ARPB4cqbn2CyEy6xBsHGH");
		PushService.setDefaultPushCallback(this, LoginActivity.class);
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.activity_login);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
