package com.example.fragmentClasses;

import com.example.thesguideproject.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


@SuppressLint("ValidFragment") 
public class InfoFragment extends Fragment{

	private TextView nameEltv;
	private String name;
	
	public InfoFragment(){}
	
	public InfoFragment(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.info_fragment, container, false);
		String s = getArguments().getString("info");  
		
		this.nameEltv = (TextView) view.findViewById(R.id.testtv);
		nameEltv.setText(s);
		
		
		return view;
	}

	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setContentView(R.layout.info_fragment);
		
		this.nameEltv = (TextView) getView().findViewById(R.id.testtv);
		nameEltv.setText("Hi!");
		
	}*/
	
	
	

}
