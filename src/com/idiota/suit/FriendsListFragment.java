package com.idiota.suit;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class FriendsListFragment extends ListFragment {

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
		String[] values = new String[] {
			"Reza Raditya", "Surya Adhiwirawan", "Nino Aquinas", "Cindy Wiryadi", "Tadeus Gary Wijono"
		};
		mFriendsList = new ArrayList<String>(Arrays.asList(values));
		mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_friends_list, mFriendsList);
		setListAdapter(mAdapter);
	}

}
