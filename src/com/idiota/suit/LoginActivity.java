package com.idiota.suit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.idiota.suit.base.BaseSuitActivity;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.PushService;

public class LoginActivity extends BaseSuitActivity {
    private Button mButton;
    private Session.StatusCallback mStatusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
            updateView();
        }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Parse.initialize(this, "U3cwotKUuI2OSBsfzITZCX9EQwW2wpjHJeRfkgDI", 
						 "83F9laWZ1ESTJJLFyr3ARPB4cqbn2CyEy6xBsHGH");
		
		PushService.setDefaultPushCallback(this, LoginActivity.class);
		ParseAnalytics.trackAppOpened(getIntent());

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

        updateView();
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
        	fetchCurrentUser(session);
        } else {
            mButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }
    
    private void fetchCurrentUser(final Session session) {
        Request request = Request.newMeRequest(session, 
                new Request.GraphUserCallback() {

            @Override
            public void onCompleted(GraphUser user, Response response) {
                // If the response is successful
                if (session == Session.getActiveSession()) {
                    if (user != null) {
                        String userId = user.getId();
                        ((App) LoginActivity.this.getApplication()).setLoggedInUserId(userId);
                        goToNextActivity();
                    }
                }
                if (response.getError() != null) {
                    // Handle error
                	Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                	LoginActivity.this.logout();
                }
            }
        });
        request.executeAsync();
    }
    
    private void goToNextActivity() {
    	Intent goToNextActivity = new Intent(getApplicationContext(), FriendsActivity.class);
    	goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(goToNextActivity);
    	finish();
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(mStatusCallback));
        } else {
            Session.openActiveSession(this, true, mStatusCallback);
        }
    }

	@Override
	protected StatusCallback getSessionStatusCallback() {
		// TODO Auto-generated method stub
		return mStatusCallback;
	}

}
