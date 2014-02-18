package com.idiota.suit.base;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.idiota.suit.R;
import com.idiota.suit.component.FriendsArrayAdapter;
import com.idiota.suit.model.FriendPreview;

public class BaseFriendsListFragment extends ListFragment {

	private FriendsArrayAdapter mAdapter;
	private ArrayList<FriendPreview> mFriendsList;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // No parent view. This fragment won't be displayed anyway.
            return null;
        }
        
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_friends_list, container, false);
        return view;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (mFriendsList == null) {
			mFriendsList = new ArrayList<FriendPreview>();
		}
		mAdapter = new FriendsArrayAdapter(getActivity(), mFriendsList);
		setListAdapter(mAdapter);
	}
	
	/*
	 * Update the friends list. The fragment can do optional filtering by overriding this method.
	 */
	public void updateFriendsList(ArrayList<FriendPreview> newList) {
		mFriendsList = new ArrayList<FriendPreview>(newList);
		if (mAdapter == null) return;
		mAdapter.clear();
		mAdapter.addAll(mFriendsList);
		mAdapter.notifyDataSetChanged();
	}

}
