package com.example.adapters;

import com.example.fragmentClasses.InfoFragment;
import com.example.fragmentClasses.OnMapFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private String name;
	private double doubleLatitude;
	private double doubleLongtitude;
	private double doubleCurrentLatitude;
	private double doubleCurrentLongtitude;
	
	 public TabsPagerAdapter(String name, double doubleLatitude, double doubleLongtitude, double doubleCurrentLatitude, double doubleCurrentLongtitude, FragmentManager fm) {
	        super(fm);
	        this.name = name;
	        this.doubleLatitude = doubleLatitude;
	        this.doubleLongtitude = doubleLongtitude;
	        this.doubleCurrentLatitude = doubleCurrentLatitude;
	        this.doubleCurrentLongtitude = doubleCurrentLongtitude;
	    }

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch (index) {
        case 0:
            // Info fragment activity
        	return new InfoFragment(name);
        case 1:
            // OnMap fragment activity
        	return new OnMapFragment(doubleLatitude, doubleLongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
        }
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
