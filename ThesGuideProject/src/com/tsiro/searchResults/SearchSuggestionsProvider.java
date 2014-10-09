package com.tsiro.searchResults;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider{

	 public final static String AUTHORITY = "com.example.searchResults.MySuggestionProvider";
	 public final static int MODE = DATABASE_MODE_QUERIES;

	    public SearchSuggestionsProvider() {
	        setupSuggestions(AUTHORITY, MODE);
	    }

	
}
