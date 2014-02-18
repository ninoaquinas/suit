package com.idiota.suit;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class BaseFriendsListFragment extends ListFragment {

	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> mFriendsList;

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
			mFriendsList = new ArrayList<String>();
		}
		mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_friends_list, mFriendsList);
		setListAdapter(mAdapter);
	}
	
	/*
	 * Update the friends list. The fragment can do optional filtering by overriding this method.
	 */
	public void updateFriendsList(List<String> newList) {
		mFriendsList = new ArrayList<String>(newList);
		if (mAdapter == null) return;
		mAdapter.clear();
		mAdapter.addAll(mFriendsList);
		mAdapter.notifyDataSetChanged();
	}

}
