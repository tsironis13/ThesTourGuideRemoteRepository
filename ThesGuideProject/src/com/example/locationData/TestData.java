package com.example.locationData;

public class TestData {

	private int id;
	private String name;
	private String surname;
	private String type;
	private String imageLink;
	
	public TestData(){}
	
	public TestData(int id, String name, String surname, String type, String imageLink){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.type = type;
		this.imageLink = imageLink;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setImageLink(String imageLink){
		this.imageLink = imageLink;
	}
	
	public String getImageLink(){
		return imageLink;
	}
}
