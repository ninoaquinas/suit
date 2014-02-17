package com.idiota.suit.base;

import com.facebook.Session;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseSuitFragmentActivity extends FragmentActivity {

	protected abstract Session.StatusCallback getSessionStatusCallback();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
	public void onResume() {
		super.onResume();
        Session session = Session.getActiveSession();
        Session.StatusCallback sessionStatusCallback = getSessionStatusCallback();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(sessionStatusCallback));
        } else {
            Session.openActiveSession(this, true, sessionStatusCallback);
        }
	}

}
