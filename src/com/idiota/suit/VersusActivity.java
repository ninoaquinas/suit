package com.idiota.suit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idiota.suit.base.BaseSuitActivity;
import com.idiota.suit.model.FriendPreview;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class VersusActivity extends BaseSuitActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_versus);
		
		Intent intent = getIntent();
		FriendPreview friend = (FriendPreview) intent.getParcelableExtra("friend");
		String friendName = friend.getName();
		String friendPhoto = friend.getPic_square();
		String friendId = friend.getUid();
		
		//set all the content of view

		ImageView friendPhotoView = (ImageView) findViewById(R.id.versus_friend_image);
		UrlImageViewHelper.setUrlDrawable(
				friendPhotoView, 
				friend.getPic_square(), 
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
		Button matchButtonView = (Button) findViewById(R.id.versus_button);
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.versus, menu);
		return true;
	}

}
