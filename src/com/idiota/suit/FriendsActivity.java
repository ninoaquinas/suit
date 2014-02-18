package com.idiota.suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idiota.suit.base.BaseFriendsListFragment;
import com.idiota.suit.base.BaseSuitFragmentActivity;
import com.idiota.suit.model.FriendPreview;

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

	protected static final String TAG = "FriendsActivity";
	
	private TabHost mTabHost;
	private HashMap<String, TabInfo> mMapTabInfo = new HashMap<String, TabInfo>();
	private TabInfo mLastTab = null;
	private boolean mHasFetchedFriends = false;
    private ArrayList<FriendPreview> mFriends = null;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		
		initialiseTabHost(savedInstanceState);
		mHasFetchedFriends = false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session.isClosed()) {
			logout();
		} else {
			fetchAllFriends();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends, menu);
		return true;
	}
	
	private Session.StatusCallback mCallback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// TODO Auto-generated method stub
			fetchAllFriends();
		}
	};
	@Override
	protected Session.StatusCallback getSessionStatusCallback() {
		return mCallback;
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
					if (mFriends != null) {
						newTab.fragment.updateFriendsList(mFriends);
					}
					
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
			FriendsSuitSentFragment.class,										/* class */
			this.mTabHost.newTabSpec("pending").setIndicator("Elo nantang"),	/* tabSpec */
			args	 															/* args */
		);
		addTab(this.mTabHost, tabInfo);
		mMapTabInfo.put(tabInfo.tag, tabInfo);
		// Set 'all friends' as first tab

		// Tab 'received'
		tabInfo = new TabInfo(
			"recvd",														/* tag */
			FriendsSuitReceivedFragment.class,								/* class */
			this.mTabHost.newTabSpec("recvd").setIndicator("Ditantang"),	/* tabSpec */
			args	 														/* args */
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
	
	private void fetchAllFriends() {
		Log.i(TAG, "Fetching all friends");
		String fqlQuery = "SELECT uid, name, pic_square FROM user WHERE uid IN " +
				"(SELECT uid2 FROM friend WHERE uid1 = me())";
		Bundle params = new Bundle();
		params.putString("q", fqlQuery);
		Session session = Session.getActiveSession();
		Request request = new Request(session,
				"/fql",                         
				params,                         
				HttpMethod.GET,                 
				new Request.Callback() {         
					public void onCompleted(Response response) {
						Log.i(TAG, "Result: " + response.toString());
					    try
					    {
					        GraphObject go  = response.getGraphObject();
					        JSONObject  jso = go.getInnerJSONObject();
					        JSONArray   arr = jso.getJSONArray( "data" );
					        
					        
					        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
					        List<FriendPreview> friends = mapper.readValue(arr.toString(), new TypeReference<List<FriendPreview>>(){});
					        
					        mFriends = new ArrayList<FriendPreview>(friends);
					        Collections.sort(
					        		mFriends, 
					        		new Comparator<FriendPreview>() {
					        		    @Override
					        		    public int compare(FriendPreview o1, FriendPreview o2) {
					        		        return o1.getName().compareTo(o2.getName());
					        		    }
					        		});
					        
							for(Entry<String, TabInfo> entry : mMapTabInfo.entrySet()) {
							    String key = entry.getKey();
							    TabInfo value = entry.getValue();
							    
							    if (value.fragment != null) {
							    	value.fragment.updateFriendsList(mFriends);
							    }
							}
					    }
					    catch (Throwable t) {
					        t.printStackTrace();
					        mHasFetchedFriends = false;
					        fetchAllFriends();
					    }
						
					}
				}); 
		if (!mHasFetchedFriends) Request.executeBatchAsync(request);
		mHasFetchedFriends = true;
	}
	
}
