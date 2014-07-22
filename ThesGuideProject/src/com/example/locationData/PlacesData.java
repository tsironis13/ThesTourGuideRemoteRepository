package com.example.locationData;

public class PlacesData {

	private int id;
	private String name_el;
	private String name_en;
	private String link;
	private double latitude; 
	private double longtitude;
	private String photo_link;
	private String genre;
	
	public PlacesData(){}
	
	public PlacesData(int id, String name_el, String name_en, String link, double latitude, double longtitude, String photo_link, String genre){
		this.id = id;
		this.name_el = name_el;
		this.name_en = name_en;
		this.link = link;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.photo_link = photo_link;
		this.genre = genre;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setNameEl(String name_el){
		this.name_el = name_el;
	}
	
	public String getNameEl(){
		return name_el;
	}
	
	public void setNameEn(String name_en){
		this.name_en = name_en;
	}
	
	public String getNameEn(){
		return name_en;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public void setLongtitude(double longtitude){
		this.longtitude = longtitude;
	}
	
	public double getLongtitude(){
		return longtitude;
	}
	
	public void setPhotoLink(String photo_link){
		this.photo_link = photo_link;
	}
	
	public String getPhotoLink(){
		return photo_link;
	}
	
	public void setGenre(String genre){
		this.genre = genre;
	}
	
	public String getGenre(){
		return genre;
	}
}
