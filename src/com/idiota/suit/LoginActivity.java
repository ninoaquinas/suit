package com.idiota.suit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class LoginActivity extends Activity {
	private TextView mTitle;
    private Button mButton;
    private Session.StatusCallback mStatusCallback = new SessionStatusCallback();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Parse.initialize(this, "U3cwotKUuI2OSBsfzITZCX9EQwW2wpjHJeRfkgDI", 
						 "83F9laWZ1ESTJJLFyr3ARPB4cqbn2CyEy6xBsHGH");
		
		/*
		PushService.setDefaultPushCallback(this, LoginActivity.class);
		ParseAnalytics.trackAppOpened(getIntent());
		*/

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, mStatusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(mStatusCallback));
            }
        }
        
		mButton = (Button) findViewById(R.id.fb_connect_button);
		mTitle = (TextView) findViewById(R.id.login_title_text);

        updateView();
	}

    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(mStatusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(mStatusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
        	mTitle.setText(session.getAccessToken());
            mButton.setText("logout");
            mButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
        } else {
        	mTitle.setText(R.string.login_title_text);
            mButton.setText(R.string.login_fbconnect_text);
            mButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(mStatusCallback));
        } else {
            Session.openActiveSession(this, true, mStatusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
        }
    }
}
