package com.idiota.suit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idiota.suit.base.BaseSuitActivity;
import com.idiota.suit.model.FriendPreview;
import com.idiota.suit.model.Suit;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class VersusActivity extends BaseSuitActivity {
	private final static int SUIT_REQUEST_CODE = 1;
	
	private FriendPreview mFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_versus);
		
		Intent intent = getIntent();
		mFriend = (FriendPreview) intent.getParcelableExtra("friend");
		
		//set all the content of view
		setupContents();
	}
	
	private void setupContents() {
		String friendName = mFriend.getName();
		
		ImageView friendPhotoView = (ImageView) findViewById(R.id.versus_friend_image);
		UrlImageViewHelper.setUrlDrawable(
				friendPhotoView, 
				mFriend.getPic_square(), 
				R.drawable.com_facebook_profile_picture_blank_square);
		
		TextView friendNameView = (TextView) findViewById(R.id.versus_friend_name);
		friendNameView.setText(friendName);
		
		//friend information depends on friend condition
		String friendInformation = "Belom maen suit nih dia :s";
		TextView friendInformationView = (TextView) findViewById(R.id.versus_friend_information);
		friendInformationView.setText(friendInformation);
		
		//match information, hide the button if there is still outstanding challenge
		TextView matchInformationView = (TextView) findViewById(R.id.versus_match_information);
		matchInformationView.setVisibility(View.GONE);
		
		//win lose stats data will be retrieved from parse
		int win = 100;
		int draw = 2;
		int lose = 40;
		TextView versusTotalKalahView = (TextView) findViewById(R.id.versus_total_kalah);
		versusTotalKalahView.setText(Integer.toString(lose));

		TextView versusTotalMenangView = (TextView) findViewById(R.id.versus_total_menang);
		versusTotalMenangView.setText(Integer.toString(win));
		
		TextView versusTotalSeriView = (TextView) findViewById(R.id.versus_total_seri);
		versusTotalSeriView.setText(Integer.toString(draw));
		
		setupButtons();
	}
	
	private void setupButtons() {
		Button versusButton = (Button) findViewById(R.id.versus_button);
		versusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	        	Intent goToNextActivity = new Intent(getApplicationContext(), SuitActivity.class);
				goToNextActivity.putExtra("friend", mFriend);
				VersusActivity.this.startActivityForResult(goToNextActivity, SUIT_REQUEST_CODE);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == SUIT_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	        	Suit suit = (Suit) data.getParcelableExtra("play");
	        	Toast.makeText(this, suit.getPlay().name(), Toast.LENGTH_SHORT).show();
	        }
	    }
	}

}
