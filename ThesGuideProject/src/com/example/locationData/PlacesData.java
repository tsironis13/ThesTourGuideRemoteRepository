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
	private String info;
	private String exhibition;
	private String menu;
	private String subcategory;
	private String tel;
	private String email;
	private String fb_link;
	
	public PlacesData(){}
	
	public PlacesData(int id, String name_el, String name_en, String link, double latitude, double longtitude, 
			String photo_link, String genre, String info, String exhibition, String menu, String subcategory, String tel, String email, String fb_link){
		this.id = id;
		this.name_el = name_el;
		this.name_en = name_en;
		this.link = link;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.photo_link = photo_link;
		this.genre = genre;
		this.info = info;
		this.exhibition = exhibition;
		this.menu = menu;
		this.subcategory = subcategory;
		this.tel = tel;
		this.email = email;
		this.fb_link = fb_link;
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
	
	public void setInfo(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setExhibition(String exhibition){
		this.exhibition = exhibition;
	}
	
	public String getExhibition(){
		return exhibition;
	}
	
	public void setMenu(String menu){
		this.menu = menu;
	}
	
	public String getMenu(){
		return menu;
	}
	
	public void setTel(String tel){
		this.tel = tel;
	}
	
	public String getTel(){
		return tel;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setFbLink(String fb_link){
		this.fb_link = fb_link;
	}
	
	public String getFbLink(){
		return fb_link;
	}
	
	public void setSubcategory(String subcategory){
		this.subcategory = subcategory;
	}
	
	public String getSubcategory(){
		return subcategory;
	}
}
