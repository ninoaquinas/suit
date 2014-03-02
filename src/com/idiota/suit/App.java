package com.idiota.suit;

import android.app.Application; 

import com.idiota.suit.model.FriendPreview;
import com.parse.Parse; 
import com.parse.ParseAnalytics;
import com.parse.PushService;

public class App extends Application { 
	private String mLoggedInUserId;

    @Override 
    public void onCreate() { 
        super.onCreate();

		Parse.initialize(this, "U3cwotKUuI2OSBsfzITZCX9EQwW2wpjHJeRfkgDI", 
						 "83F9laWZ1ESTJJLFyr3ARPB4cqbn2CyEy6xBsHGH");
    }
    
    public void setLoggedInUserId(String loggedInUserId) {
    	mLoggedInUserId = loggedInUserId;
    }
    
    public String getLoggedInUserId() {
    	return mLoggedInUserId;
    }
} 
