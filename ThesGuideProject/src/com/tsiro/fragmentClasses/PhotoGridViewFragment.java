package com.tsiro.fragmentClasses;

import com.tsiro.thesguideproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tsiro.adapters.TabsPagerAdapter;
import com.tsiro.thesguideproject.PlacesDetailsTabs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment") 
public class PhotoGridViewFragment extends Fragment{

	//PageIndicator pageIndicator;
	//ImagesSlidesFragmentAdapter mAdapter;
	private static final String STATE_POSITION = "STATE_POSITION";
	private GridView photoFragmentGridView;
	private Button refreshGridViewbutton;
	//private String[] imageUrls = new String[4];
	//private ArrayList<String> imageUrls = new ArrayList<String>(4);
	//private Context context;
	
	DisplayImageOptions options;
	ImageLoader imageLoader;
	TabsPagerAdapter tabsPagerAdapter;
	//private ArrayList<String> photoList;
	private String[] photoList;
	private int screen_height;
	private int screen_width;
	private String language;
	ViewPager pager;
	PlacesDetailsTabs pdt = new PlacesDetailsTabs();
	/*String[] web = {
	        "Google",
	      "Github",
	      "Instagram",
	      "Facebook",
	      "Flickr",
	      "Pinterest",
	      "Quora",
	      "Twitter",
	      "Vimeo",
	      "WordPress",
	      "Youtube",
	      "Stumbleupon",
	      "SoundCloud",
	      "Reddit",
	      "Blogger"
	  } ;
	
	public Drawable d;*/
	
	public PhotoGridViewFragment(){}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		photoList = getArguments().getStringArray("linksList");
		
		screen_height = getArguments().getInt("Screen Height");
		screen_width = getArguments().getInt("Screen Width");
		language = getArguments().getString("language");
		int photoList_array = photoList.length;
		for (int i=0; i<photoList.length; i++){
			//Log.i("PhotoList", photoList[i]);
		}
		String photoListLength = Integer.toString(photoList_array);
		
		
		//imageLoader.destroy();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		
		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		
		View view = inflater.inflate(R.layout.photo_fragment, container, false);
		//view.setMinimumHeight(screen_height/8);
		this.photoFragmentGridView = (GridView) view.findViewById(R.id.photofragmentGridView);
		this.refreshGridViewbutton = (Button) view.findViewById(R.id.refreshgridviewbutton);
		
		photoFragmentGridView.setAdapter(new ImageAdapter());
		
	    return view;
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void startImagePagerActivity(int position) {
		//Intent intent = new Intent(getActivity(), PhotoPagerActivity.class);
		//intent.putExtra("id", position);
		//intent.putExtra("photoList", photoList);
		//startActivity(intent);
		//Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
		//intent.putExtra("PhotoList", photoList);
		//intent.putExtra("PhotoPosition", position);
		//startActivity(intent);
	}
	
	public class ImageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return photoList.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = getLayoutInflater(getArguments()).inflate(R.layout.item_grid_image, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			
		imageLoader.displayImage(photoList[position], holder.imageView, options, new SimpleImageLoadingListener() {
				 @Override
				 public void onLoadingStarted(String imageUri, View view) {
					 holder.progressBar.setProgress(0);
					 holder.progressBar.setVisibility(View.VISIBLE);
				 }

				 @Override
				 public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					 	 holder.progressBar.setVisibility(View.GONE);
					 	 if (language.equals("English")){
					 		 refreshGridViewbutton.setVisibility(View.VISIBLE);
					 	 }else{		
					 		refreshGridViewbutton.setVisibility(View.VISIBLE);
					 		refreshGridViewbutton.setText("Ανανέωσε");
					 	 }
				 }

				 @Override
				 public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					 holder.progressBar.setVisibility(View.GONE);
					 refreshGridViewbutton.setVisibility(View.GONE);
				 }
			 }, new ImageLoadingProgressListener() {
				 @Override
				 public void onProgressUpdate(String imageUri, View view, int current, int total) {
					 holder.progressBar.setProgress(Math.round(100.0f * current / total));
				 }
			 }
);
		
		
			
			return view;
			
		}
		
	}
	
	static class ViewHolder {
		Button button;
		ImageView imageView;
		ProgressBar progressBar;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		refreshGridViewbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
				if (wifi.isWifiEnabled()){
						photoFragmentGridView.setAdapter(new ImageAdapter());
				}else{
					if (language.equals("English")){
						Toast.makeText(getActivity(), "Please enable WI-FI widget to view museum images", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getActivity(), "Πα�?ακαλ�? ενε�?γοποίησε το WI-FI για να δεις τις εικ�?νες του μουσείου", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		photoFragmentGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
								
				//Bundle b = new Bundle();
				//b.putString("hello", "hello");
				//pdt.methodsss(3, InfoFragment.class, b);
				
				
				//FragmentManager fm = getFragmentManager();
		        //FragmentTransaction ft = fm.beginTransaction();
		        //ViewPagerFragment vpf = new ViewPagerFragment();
		        
		        //ft.replace(R.id.photofragmentGridView, vpf);
		       // ft.addToBackStack(null);
		       // ft.commit();
		
				
			}
		});
		//photoList = getArguments().getStringArrayList("linksList");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		//imageLoader.destroy();
		//imageLoader.stop();
	}

	
	
	
	
	

}
