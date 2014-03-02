package com.idiota.suit.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Session;
import com.facebook.SessionState;
import com.idiota.suit.App;
import com.idiota.suit.LoginActivity;
import com.idiota.suit.R;

public abstract class BaseSuitFragmentActivity extends FragmentActivity {

	private Session.StatusCallback mCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
	        Session activeSession = Session.getActiveSession();
	        if (!activeSession.isOpened()) {
	        	logout();
	        }
		}
	};
	
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
		return mCallback;
	}

    protected void logout() {
        ((App) getApplication()).setLoggedInUserId(null);
        
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.common_menu, menu);
		return true;
	}

}
