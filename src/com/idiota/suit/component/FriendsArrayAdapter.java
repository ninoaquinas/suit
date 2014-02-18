package com.idiota.suit.component;

import java.util.ArrayList;

import com.idiota.suit.R;
import com.idiota.suit.model.FriendPreview;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsArrayAdapter extends ArrayAdapter<FriendPreview> {
	
	private Activity mActivity;
	
	public FriendsArrayAdapter(Activity activity, ArrayList<FriendPreview> friends) {
		super(activity,0, friends);
		mActivity = activity;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		FriendPreview item = getItem(position);
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
		
		return view;
	}
}
