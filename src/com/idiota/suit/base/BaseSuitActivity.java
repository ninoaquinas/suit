package com.idiota.suit.base;

import com.facebook.Session;
import com.facebook.SessionState;

import android.app.Activity;

public abstract class BaseSuitActivity extends Activity {
	
	protected abstract Session.StatusCallback getSessionStatusCallback();
	
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
