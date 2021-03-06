package com.idiota.suit.component;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.idiota.suit.R;
import com.idiota.suit.VersusActivity;
import com.idiota.suit.model.FriendPreview;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class FriendsArrayAdapter extends ArrayAdapter<FriendPreview> {
	
	private Activity mActivity;
	
	public FriendsArrayAdapter(Activity activity, ArrayList<FriendPreview> friends) {
		super(activity,0, friends);
		mActivity = activity;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final FriendPreview item = getItem(position);
		if (item == null) {
			return null;
		}
		if (view == null) {
			view = mActivity.getLayoutInflater().inflate(R.layout.row_friends_list, parent, false);
		}
		
		// Setting image
		ImageView imageView = (ImageView) view.findViewById(R.id.row_friend_image);
		UrlImageViewHelper.setUrlDrawable(imageView, item.getPic_square(), R.drawable.com_facebook_profile_picture_blank_square);
		
		// Setting name
		TextView textView = (TextView) view.findViewById(R.id.row_friend_name);
		textView.setText(item.getName());
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goToNextActivity = new Intent(mActivity.getApplicationContext(), VersusActivity.class);
				goToNextActivity.putExtra("friend", item);
				mActivity.startActivity(goToNextActivity);
			}
		});
		
		return view;
	}
}
