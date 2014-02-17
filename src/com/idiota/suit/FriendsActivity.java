package com.idiota.suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.idiota.suit.base.BaseSuitFragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class FriendsActivity extends BaseSuitFragmentActivity implements TabHost.OnTabChangeListener{
	// Helper classes
	private class TabInfo {
		private String tag;
		private Class<? extends BaseFriendsListFragment> clazz;
		private TabHost.TabSpec tabSpec;
		private Bundle args;
		private BaseFriendsListFragment fragment;
		TabInfo(String tag, Class clazz, TabHost.TabSpec tabSpec, Bundle args) {
			this.tag = tag;
			this.clazz = clazz;
			this.tabSpec = tabSpec;
			this.args = args;
		}
	}
	private class TabFactory implements TabContentFactory {
		private final Context mContext;
	
		public TabFactory(Context context) {
			mContext = context;
		}
	
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}
	
	private TabHost mTabHost;
	private HashMap<String, TabInfo> mMapTabInfo = new HashMap<String, TabInfo>();
	private TabInfo mLastTab = null;
    private Session.StatusCallback mStatusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
            return;
        }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		
		initialiseTabHost(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends, menu);
		return true;
	}
	
	@Override
	public void onTabChanged(String tag) {
		TabInfo newTab = (TabInfo) mMapTabInfo.get(tag);
		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
			if (mLastTab != null) {
				if (mLastTab.fragment != null) {
					ft.detach(mLastTab.fragment);
				}
			}
			if (newTab != null) {
				if (newTab.fragment == null) {
					// Create the fragment
					newTab.fragment = (BaseFriendsListFragment) Fragment.instantiate(this,
							newTab.clazz.getName(), newTab.args);
					
					// Injects friends list
					String[] values = new String[] {
						"Reza Raditya", "Surya Adhiwirawan", "Nino Aquinas", "Cindy Wiryadi", "Tadeus Gary Wijono"
					};
					newTab.fragment.updateFriendsList(Arrays.asList(values));
					
					ft.add(android.R.id.tabcontent, newTab.fragment, newTab.tag);
				} else {
					ft.attach(newTab.fragment);
				}
			}

			mLastTab = newTab;
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}
	}

	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		TabInfo first = null, tabInfo = null;
		
		// Tab 'all friends'
		tabInfo = new TabInfo(
			"all",																/* tag */
			FriendsAllFragment.class,											/* class */
			this.mTabHost.newTabSpec("all").setIndicator("Semua Temen"),		/* tabSpec */
			args	 															/* args */
		);
		addTab(this.mTabHost, tabInfo);
		mMapTabInfo.put(tabInfo.tag, tabInfo);
		// Set 'all friends' as first tab
		first = tabInfo;

		// Tab 'pending'
		tabInfo = new TabInfo(
			"pending",															/* tag */
			FriendsSuitSentFragment.class,											/* class */
			this.mTabHost.newTabSpec("pending").setIndicator("Elo nantang"),	/* tabSpec */
			args	 															/* args */
		);
		addTab(this.mTabHost, tabInfo);
		mMapTabInfo.put(tabInfo.tag, tabInfo);
		// Set 'all friends' as first tab

		// Tab 'received'
		tabInfo = new TabInfo(
			"recvd",															/* tag */
			FriendsSuitReceivedFragment.class,											/* class */
			this.mTabHost.newTabSpec("recvd").setIndicator("Elo ditantang"),	/* tabSpec */
			args	 															/* args */
		);
		addTab(this.mTabHost, tabInfo);
		mMapTabInfo.put(tabInfo.tag, tabInfo);
		// Set 'all friends' as first tab
		
		// Default to first tab
		if (first != null) {
			onTabChanged(first.tag);
		}
		
		mTabHost.setOnTabChangedListener(this);
	}
	
	private void addTab(TabHost tabHost, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		TabHost.TabSpec tabSpec = tabInfo.tabSpec;
		tabSpec.setContent(new TabFactory(this));
		String tag = tabSpec.getTag();

		// Check to see if we already have a fragment for this tab, probably
		// from a previously saved state.  If so, deactivate it, because our
		// initial state is that a tab isn't shown.
		tabInfo.fragment = (BaseFriendsListFragment) getSupportFragmentManager().findFragmentByTag(tag);
		if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.detach(tabInfo.fragment);
			ft.commit();
			getSupportFragmentManager().executePendingTransactions();
		}

		tabHost.addTab(tabSpec);
	}

	@Override
	protected StatusCallback getSessionStatusCallback() {
		// TODO Auto-generated method stub
		return mStatusCallback;
	}
}
