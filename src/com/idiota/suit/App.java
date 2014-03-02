package com.idiota.suit;

import android.app.Application; 

import com.parse.Parse; 
import com.parse.ParseAnalytics;
import com.parse.PushService;

public class App extends Application { 

    @Override public void onCreate() { 
        super.onCreate();

		Parse.initialize(this, "U3cwotKUuI2OSBsfzITZCX9EQwW2wpjHJeRfkgDI", 
						 "83F9laWZ1ESTJJLFyr3ARPB4cqbn2CyEy6xBsHGH");
    }
} 
