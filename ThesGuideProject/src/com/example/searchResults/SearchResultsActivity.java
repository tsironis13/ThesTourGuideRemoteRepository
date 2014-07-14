package com.example.searchResults;

//import com.example.thesguideproject.R;

import com.example.thesguideproject.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class SearchResultsActivity extends Activity{

	Bundle bundle;
	private TextView txtQuery;
	
	private static final String TAG = SearchResultsActivity.class.getSimpleName();
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.search_results);
	        
	        // get the action bar
	        android.app.ActionBar actionBar = getActionBar();
	 
	        // Enabling Back navigation on Action Bar icon
	        actionBar.setDisplayHomeAsUpEnabled(true);
	 
	        txtQuery = (TextView) findViewById(R.id.txtQuery);
	 
	        
	        final Intent queryIntent = getIntent();
	        
	        final String queryAction = queryIntent.getAction();
	        if(Intent.ACTION_SEARCH.equals(queryAction)){
	        	//this.doSearchQuery(queryIntent);
	        }
	        else if(Intent.ACTION_VIEW.equals(queryAction)){
	        	//this.doView(queryIntent);
	        }
	        else{
	        	Log.d(TAG, "Create intent NOT from search");
	        }
	        handleIntent(getIntent());
	 } 
	 
	 
	@Override
	  protected void onNewIntent(Intent queryIntent) {
		
		 setIntent(queryIntent);
	     handleIntent(queryIntent);
		
		  super.onNewIntent(queryIntent);
	        final String queryAction = queryIntent.getAction();
	        if (Intent.ACTION_SEARCH.equals(queryAction)) {
	           // this.doSearchQuery(queryIntent);
	        } else if (Intent.ACTION_VIEW.equals(queryAction)) {
	            //this.doView(queryIntent);
	        }
	    }
	 
	 
	private void doSearchQuery(Intent queryIntent) {
		// TODO Auto-generated method stub
		String queryString = queryIntent.getDataString(); // from suggestions
		
		if (queryString == null){
			queryString = queryIntent.getStringExtra(SearchManager.QUERY); // from search-bar
			
			 SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
	         suggestions.saveRecentQuery(queryString, null);
		}
		
		
		// display results here
		
		bundle = getIntent().getExtras();
        bundle.putString("user_query", queryString);
        queryIntent.setData(Uri.fromParts("", "", queryString));
 
        queryIntent.setAction(Intent.ACTION_SEARCH);
        queryIntent.putExtras(bundle);
        startActivity(queryIntent);
	}
	
	private void doView(Intent queryIntent) {
		// TODO Auto-generated method stub
		Uri uri = queryIntent.getData();
        String action = queryIntent.getAction();
        Intent intent = new Intent(action);
        intent.setData(uri);
        startActivity(intent);
        this.finish();
	}


	 /**
	     * Handling intent data
	     */
	    private void handleIntent(Intent intent) {
	        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	            String query = intent.getStringExtra(SearchManager.QUERY);
	 
	            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
	            suggestions.saveRecentQuery(query, null);
	            
	            /**
	             * Use this query to display search results like
	             * 1. Getting the data from SQLite and showing in listview
	             * 2. Making webrequest and displaying the data
	             * For now we just display the query only
	             */
	            txtQuery.setText("Search Query: " + query);
	 
	        }
	 
	    }
	 
}
