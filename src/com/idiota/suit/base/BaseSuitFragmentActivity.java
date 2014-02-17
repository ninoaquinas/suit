package com.idiota.suit.base;

import com.facebook.Session;
import com.facebook.SessionState;
import com.idiota.suit.LoginActivity;
import com.idiota.suit.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public abstract class BaseSuitFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(getSessionStatusCallback());
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(getSessionStatusCallback());
    }

	protected Session.StatusCallback getSessionStatusCallback() {
		return new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
		        Session activeSession = Session.getActiveSession();
		        if (!activeSession.isOpened()) {
		        	logout();
		        }
			}
		};
	}

    protected void logout() {
        Session activeSession = Session.getActiveSession();
        if (activeSession != null) {
        	activeSession.closeAndClearTokenInformation();;
        }
    	Intent goToLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
    	goToLoginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(goToLoginActivity);
    	finish();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	            logout();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
