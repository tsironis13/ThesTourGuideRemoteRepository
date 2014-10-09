package com.tsiro.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.example.thesguideproject.R;
import com.tsiro.thesguideproject.PlacesDetailsTabs;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

@SuppressLint("UseSparseArrays") 
public class TabsPagerAdapter extends FragmentStatePagerAdapter implements OnPageChangeListener, TabListener {
	
	//private final Context mContext;
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	private final String TAG = "";
	private Map<Integer, Stack<TabInfo>> history = new HashMap<Integer, Stack<TabInfo>>();
	private final PlacesDetailsTabs activity;
	private int TOTAL_TABS;
	
	
	static final class TabInfo{
		private final Class<?> clss;
		private final Bundle args;
		
		TabInfo(Class<?> _class, Bundle _args){
			clss = _class;
			args = _args;
		}
	}
	
	//@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	//public TabsPagerAdapter(FragmentActivity activity, ViewPager pager) {
		//super(activity.getSupportFragmentManager());
		//mContext = activity;
		//mActionBar = activity.getActionBar();
		//mViewPager = pager;
		//mViewPager.setAdapter(this);
		//mViewPager.setOnPageChangeListener(this);
        //this.name = name;
        //this.doubleLatitude = doubleLatitude;
        //this.doubleLongtitude = doubleLongtitude;
        //this.doubleCurrentLatitude = doubleCurrentLatitude;
        //this.doubleCurrentLongtitude = doubleCurrentLongtitude;
	//}
	
	
	public TabsPagerAdapter(PlacesDetailsTabs activity, ViewPager pager, ActionBar mActionBar) {
		super(activity.getSupportFragmentManager());
		this.activity = activity;
		//mActionBar = activity.getSupportActionBar();
		//mActionBar = activity.getSupportActionBar();
		this.mActionBar = mActionBar;
		
		//mActionBar = activity.getActionBar();
		mViewPager = pager;
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
        //this.name = name;
        //this.doubleLatitude = doubleLatitude;
        //this.doubleLongtitude = doubleLongtitude;
        //this.doubleCurrentLatitude = doubleCurrentLatitude;
        //this.doubleCurrentLongtitude = doubleCurrentLongtitude;
	}
	
	
	public void addTab(Tab tab, Class<?> clss, Bundle args){
		TabInfo info = new TabInfo(clss, args);
		tab.setTag(info);
		tab.setTabListener(this);
		mTabs.add(info);
		mActionBar.addTab(tab);
		notifyDataSetChanged();
	}
	
/*	public TabsPagerAdapter(String name, double doubleLatitude, double doubleLongtitude, double doubleCurrentLatitude, double doubleCurrentLongtitude, FragmentManager fm) {
	        super(fm);
	        this.name = name;
	        this.doubleLatitude = doubleLatitude;
	        this.doubleLongtitude = doubleLongtitude;
	        this.doubleCurrentLatitude = doubleCurrentLatitude;
	        this.doubleCurrentLongtitude = doubleCurrentLongtitude;
	    }*/

	/*@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch (index) {
        case 0:
            // Info fragment activity
        	return new InfoFragment(name);
        case 1:
            // OnMap fragment activity
        	return new MenuFragment();  	
		case 2:
			return new OnMapFragment(doubleLatitude, doubleLongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
		}
		return null;
	}*/
	
	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		//Toast.makeText(mContext, info.clss.getName(), Toast.LENGTH_SHORT).show();
		if (info.clss.getName().equals("com.example.fragmentClasses.PhotoGridViewFragment")){
			//Toast.makeText(mContext, "!!PhotoGridViewFragment", Toast.LENGTH_SHORT).show();
			return Fragment.instantiate(activity, "com.example.fragmentClasses.PhotoGridViewFragment", info.args);
		}
		else if (info.clss.getName().equals("com.example.fragmentClasses.OnMapFragment")) {
			//Toast.makeText(mContext, "!!OnMapFragment", Toast.LENGTH_SHORT).show();
			return Fragment.instantiate(activity, "com.example.fragmentClasses.OnMapFragment", info.args);
		}	
		else{	
			return Fragment.instantiate(activity, info.clss.getName(), info.args);
		}
	}

	
	
	@Override
	public int getItemPosition(final Object object) {
		// TODO Auto-generated method stub
		/* Get the current position. */
		int position = mActionBar.getSelectedTab().getPosition();
		
		/* The default value. */
	    int pos = POSITION_NONE;
	    if (history.get(position).isEmpty()) {
	        return POSITION_NONE;
	    }

	    /* Checks if the object exists in current history. */
	    for (Stack<TabInfo> stack : history.values()) {
	        TabInfo c = stack.peek();
	        if (c.getClass().getName().equals(object.getClass().getName())) {
	            pos = POSITION_UNCHANGED;
	            break;
	        }
	    }
	    return pos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTabs.size();
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		mActionBar.setSelectedNavigationItem(position);
	}

	/*@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}*/

	
	@SuppressWarnings("rawtypes")
	public void replace(final int position, final Class fragmentClass, final Bundle args) {
	    /* Save the fragment to the history. */
		activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();

	    /* Update the tabs. */
	    updateTabs(new TabInfo(fragmentClass, args), position);

	    /* Updates the history. */
	    history.get(position).push(new TabInfo(mTabs.get(position).clss, mTabs.get(position).args));

	    notifyDataSetChanged();
	}
	
	private void updateTabs(final TabInfo tabInfo, final int position) {
	    mTabs.remove(position);
	    mTabs.add(position, tabInfo);
	    mActionBar.getTabAt(position).setTag(tabInfo);
	}
	
	public void createHistory() {
	    int position = 0;
	    TOTAL_TABS = mTabs.size();
	    for (TabInfo mTab : mTabs) {
	        if (history.get(position) == null) {
	            history.put(position, new Stack<TabInfo>());
	        }
	        history.get(position).push(new TabInfo(mTab.clss, mTab.args));
	        position++;
	    }
	}
	
	
	public void back() {
	    int position = mActionBar.getSelectedTab().getPosition();
	    if (!historyIsEmpty(position)) {
	        /* In case there is not any other item in the history, then finalize the activity. */
	        if (isLastItemInHistory(position)) {
	            activity.finish();
	        }
	        final TabInfo currentTabInfo = getPrevious(position);
	        mTabs.clear();
	        for (int i = 0; i < TOTAL_TABS; i++) {
	            if (i == position) {
	                mTabs.add(new TabInfo(currentTabInfo.clss, currentTabInfo.args));
	            } else {
	                TabInfo otherTabInfo = history.get(i).peek();
	                mTabs.add(new TabInfo(otherTabInfo.clss, otherTabInfo.args));
	            }
	        }
	    }
	    mActionBar.selectTab(mActionBar.getTabAt(position));
	    notifyDataSetChanged();
	}
	
	private boolean historyIsEmpty(final int position) {
	    return history == null || history.isEmpty() || history.get(position).isEmpty();
	}

	private boolean isLastItemInHistory(final int position) {
	    return history.get(position).size() == 1;
	}
	
	private TabInfo getPrevious(final int position) {
	    TabInfo currentTabInfo = history.get(position).pop();
	    if (!history.get(position).isEmpty()) {
	        currentTabInfo = history.get(position).peek();
	    }
	    return currentTabInfo;
	}

	@Override
	public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
		String tab_position = Integer.toString(tab.getPosition());
		Log.v(TAG,  tab_position + "was clicked");
		Object tag = tab.getTag();
		for (int i = 0; i<mTabs.size(); i++){
			if (mTabs.get(i) == tag){
				mViewPager.setCurrentItem(i);
			}
		}
	}

	@Override
	public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


}
