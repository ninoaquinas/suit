package com.idiota.suit;

import com.idiota.suit.model.FriendPreview;
import com.idiota.suit.model.Suit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SuitActivity extends Activity {
	public final static int SUIT_GAJAH = 1;
	public final static int SUIT_ORANG = 2;
	public final static int SUIT_SEMUT = 3;
	
	private FriendPreview mFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suit);

		Intent intent = getIntent();
		mFriend = (FriendPreview) intent.getParcelableExtra("friend");
		String friendName = mFriend.getName();

		TextView friendNameView = (TextView) findViewById(R.id.suit_opponent);
		friendNameView.setText(friendName);
		
		setupButtons();
	}
	
	private void setupButtons() {
		Button gajahButton = (Button) findViewById(R.id.suit_gajah);
		gajahButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("play", new Suit(Suit.Play.GAJAH));
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});

		Button orangButton = (Button) findViewById(R.id.suit_orang);
		orangButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("play", new Suit(Suit.Play.ORANG));
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});

		Button semutButton = (Button) findViewById(R.id.suit_semut);
		semutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("play", new Suit(Suit.Play.SEMUT));
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
	}

}
