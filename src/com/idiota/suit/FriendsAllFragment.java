package com.idiota.suit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FriendsAllFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // No parent view. This fragment won't be displayed anyway.
            return null;
        }
        
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_friends_list, container, false);
        ((TextView) view.findViewById(R.id.dummy_text)).setText("Tab pertama");
        return view;
    }

}
