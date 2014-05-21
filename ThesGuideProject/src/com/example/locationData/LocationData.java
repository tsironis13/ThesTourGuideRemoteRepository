package com.example.locationData;

public class LocationData {

	private String id;
	private String genre;
	private String description;
	private String photo_link;
	private String name_el;
	private String latitude;
	private String longtitude;
	
	public LocationData(){}
	
	public LocationData(String genre, String photo_link, String name_el, String latitude, String longtitude){
		
		//this.id = id;
		this.genre = genre;
		//this.description = description;
		this.photo_link = photo_link;
		this.name_el = name_el;		
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setGenre(String genre){
		this.genre = genre;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setPhotoLink(String photo_link){
		this.photo_link = photo_link;
	}
	
	public String getPhotoLink(){
		return photo_link;
	}
	
	public void setNameEl(String name_el){
		this.name_el = name_el;
	}
	
	public String getNameEl(){
		return name_el;
	}
	
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	
	public String getLatitude(){
		return latitude;
	}
	
	public void setLongtitude(String longtitude){
		this.longtitude = longtitude;
	}
	
	public String getLongtitude(){
		return longtitude;
	}
}
