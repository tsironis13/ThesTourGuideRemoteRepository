package com.example.adapters;

import java.util.ArrayList;

import com.example.fragmentClasses.InfoFragment;
import com.example.fragmentClasses.MenuFragment;
import com.example.fragmentClasses.OnMapFragment;
//import com.rufflez.swipeyandlist.TabsAdapter.TabInfo;


import com.example.adapters.TabsPagerAdapter.TabInfo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class TabsPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener, TabListener {
	
	private final Context mContext;
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	private final String TAG = "";
	private String name;
	private double doubleLatitude;
	private double doubleLongtitude;
	private double doubleCurrentLatitude;
	private double doubleCurrentLongtitude;
	
	static final class TabInfo{
		private final Class<?> clss;
		private final Bundle args;
		
		TabInfo(Class<?> _class, Bundle _args){
			clss = _class;
			args = _args;
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	public TabsPagerAdapter(FragmentActivity activity, ViewPager pager) {
		super(activity.getSupportFragmentManager());
		mContext = activity;
		mActionBar = activity.getActionBar();
		mViewPager = pager;
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
        //this.name = name;
        //this.doubleLatitude = doubleLatitude;
        //this.doubleLongtitude = doubleLongtitude;
        //this.doubleCurrentLatitude = doubleCurrentLatitude;
        //this.doubleCurrentLongtitude = doubleCurrentLongtitude;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
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
		return Fragment.instantiate(mContext, info.clss.getName(), info.args);
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
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
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
