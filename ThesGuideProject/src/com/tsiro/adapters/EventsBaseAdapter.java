package com.tsiro.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.tsiro.thesguideproject.R;
import com.tsiro.fragmentClasses.CalendarFragment;
import com.tsiro.fragmentClasses.ListPlacesFragment;
import com.tsiro.fragmentClasses.ListPlacesFragment.MyViewHolder;
import com.tsiro.locationData.PlacesData;
import com.tsiro.myLocation.GPSTracker;
import com.tsiro.tasks.PlacesJsonWebApiTask;
import com.tsiro.thesguideproject.PlacesDetailsTabs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventsBaseAdapter extends BaseAdapter implements OnClickListener{

	private ArrayList<PlacesData> locations;
	private LayoutInflater layoutInflater;
	private ListPlacesFragment activity;
	private CalendarFragment a;
	private PlacesJsonWebApiTask c;
	private OnItemClickListener onclick;
	private Context context;
	private int layout;
	private String button_pressed;
	private double current_latitude;
	private double current_longtitude;
	private String flag;
	private boolean imagessavedFlag;
	
	public EventsBaseAdapter(String button_pressed, OnItemClickListener onItemClickListener, Context context, int layout, ArrayList<PlacesData> locations, double current_latitude, double current_longtitude, String flag, boolean imagessavedFlag){
		this.button_pressed = button_pressed;
		this.onclick = onItemClickListener;
		this.context = context;
		this.layout = layout;
		this.locations = locations;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		this.flag = flag;
		this.imagessavedFlag = imagessavedFlag;
	}
	
	public EventsBaseAdapter(String button_pressed, CalendarFragment activity, Context context, int layout, ArrayList<PlacesData> locations, double current_latitude, double current_longtitude, String flag, boolean imagessavedFlag){
		this.button_pressed = button_pressed;
		this.a = activity;
		this.context = context;
		this.layout = layout;
		this.locations = locations;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		this.flag = flag;
		this.imagessavedFlag = imagessavedFlag;
	}
	
	public EventsBaseAdapter(String button_pressed, PlacesJsonWebApiTask activity, Context context, int layout, ArrayList<PlacesData> locations, double current_latitude, double current_longtitude, String flag, boolean imagessavedFlag){
		this.button_pressed = button_pressed;
		this.c = activity;
		this.context = context;
		this.layout = layout;
		this.locations = locations;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		this.flag = flag;
		this.imagessavedFlag = imagessavedFlag;
	}
	
	/*public EventsBaseAdapter(String button_pressed, ListPlacesFragment activity, Context context, int layout, ArrayList<PlacesData> locations, double current_latitude, double current_longtitude){
		this.button_pressed = button_pressed;
		this.activity = activity;
		this.context = context;
		this.layout = layout;
		this.locations = locations;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
	}*/
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.locations.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder holder;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, parent, false);
			holder = new MyViewHolder();
			holder.placeNametv = (TextView) convertView.findViewById(R.id.placeNametv);
			holder.nameEllower = (TextView) convertView.findViewById(R.id.nameellower);
			holder.latitudetv = (TextView) convertView.findViewById(R.id.latitudetv);
			holder.longtitudetv = (TextView) convertView.findViewById(R.id.longtitudetv);
			holder.distance = (TextView) convertView.findViewById(R.id.distance);
			holder.icon = (ImageView) convertView.findViewById(R.id.locationImage);
			holder.desc_infohiddentv = (TextView) convertView.findViewById(R.id.descinfohiddentv);
			holder.menuhiddentv = (TextView) convertView.findViewById(R.id.menuhiddentv);
			holder.telhiddentv = (TextView) convertView.findViewById(R.id.telhiddentv);
			holder.linkhiddentv = (TextView) convertView.findViewById(R.id.linkhiddentv);
			holder.fbLinkhiddentv = (TextView) convertView.findViewById(R.id.fbLinkhiddentv);
			holder.emailhiddentv = (TextView) convertView.findViewById(R.id.emailhiddentv);
			holder.exhibitionhiddentv = (TextView) convertView.findViewById(R.id.exhibitionhiddentv);
			holder.infoButton = (Button) convertView.findViewById(R.id.info_button);
			holder.infoButton.setTag(holder);
			convertView.setTag(holder);
		}
		else{
			holder = (MyViewHolder) convertView.getTag();
		}
		
		PlacesData location = locations.get(position);
		holder.locations = location;
		
		double apostasi = GPSTracker.getDistance(this.current_latitude, this.current_longtitude, location.getLatitude(), location.getLongtitude());
		double distanceInKm = apostasi/1000;
		DecimalFormat df = new DecimalFormat("#.##");
		String dx=df.format(distanceInKm);
		
		if(location.getPhotoLink().equals("")) {
			RelativeLayout imgHolder = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
   			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
   			holder.icon.setLayoutParams(params);
		}
		
		holder.placeNametv.setText(location.getNameEl());
		holder.nameEllower.setText(location.getNameElLower());
		holder.distance.setText(dx + " ÷ëì");
		holder.desc_infohiddentv.setText(location.getInfo());
		holder.menuhiddentv.setText(location.getMenu());
		holder.telhiddentv.setText(location.getTel());
		holder.linkhiddentv.setText(location.getLink());
		holder.fbLinkhiddentv.setText(location.getFbLink());
		holder.emailhiddentv.setText(location.getEmail());
		holder.exhibitionhiddentv.setText(location.getExhibition());
		String latitude = Double.toString(location.getLatitude());
		holder.latitudetv.setText(latitude);
		String longtitude = Double.toString(location.getLongtitude());
		holder.longtitudetv.setText(longtitude);
		holder.infoButton.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View view) {
		MyViewHolder vH = (MyViewHolder) view.getTag();
		
		
		   Intent intent = new Intent(context, PlacesDetailsTabs.class);
		   String str_current_latitude = Double.toString(current_latitude);
		   String str_current_longtitude = Double.toString(current_longtitude);
		   intent.putExtra("language", "Greek");
		   intent.putExtra("current latitude" , str_current_latitude);
		   intent.putExtra("current longtitude", str_current_longtitude);
		   intent.putExtra("placeNameEl", vH.placeNametv.getText());
		   intent.putExtra("placenameEllower", vH.nameEllower.getText());
		   intent.putExtra("desc_info", vH.desc_infohiddentv.getText());
		   intent.putExtra("menu", vH.menuhiddentv.getText());
		   intent.putExtra("telephone", vH.telhiddentv.getText());
		   intent.putExtra("link", vH.linkhiddentv.getText());
		   intent.putExtra("fbLink", vH.fbLinkhiddentv.getText());
		   intent.putExtra("email", vH.emailhiddentv.getText());
		   intent.putExtra("exhibition", vH.exhibitionhiddentv.getText());
		   intent.putExtra("latitude", vH.latitudetv.getText());
		   intent.putExtra("longtitude", vH.longtitudetv.getText());
		   intent.putExtra("button_pressed_text", button_pressed);
		   intent.putExtra("imagessavedFlag", imagessavedFlag);
		   intent.putExtra("displaycurrentPoint", "yes");
		   
	   if (flag.equals("task")){	
		   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   this.context.startActivity(intent);
	    }
	   else{
		   this.context.startActivity(intent);
	   }
		//this.activity.startActivity(intent);
		/*if (this.activity == null){
			this.searchPlaceResultListFragment.startActivity(intent);
		}else{
			this.activity.startActivity(intent);
		}*/
	}

}
