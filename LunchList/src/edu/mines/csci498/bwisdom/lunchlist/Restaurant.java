package edu.mines.csci498.bwisdom.lunchlist;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Restaurant {
	private String name = "";
	private String address = "";
	private String type = "";
	private int day,month,year;

	public String getName() {
		return (name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return (address);
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getType(){
		return (type);
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getDate(){
		String dateString = month +"/"+day+"/"+year;
		//String dateString = Integer.toString(this.date.get(Calendar.MONTH));
		//dateString+="/";
		//dateString += Integer.toString(date.get(Calendar.DATE));
		//dateString+="/";
		//dateString += Integer.toString(date.get(Calendar.YEAR));
		return (dateString);
	}
	
	public void setDate(int month,int day, int year){
		this.month = month+1;
		this.day = day;
		this.year = year;
	}
	
	public String toString(){
		return(getName());
	}

}
