package com.walmart.tickets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SeatHold{
	
	public String emailId = null;
	public int seatHoldId;
	public long expiryTime;
	
	List<String> seats=new ArrayList<String>();
	
	public SeatHold() {
		
	}


	public SeatHold(String emailId, List<String> seats) {
		this.setSeats(seats);
		this.setEmailId(emailId);
		this.setseatHoldId();
		long expTime = Calendar.getInstance().getTimeInMillis()+ (1 * 60 * 1000);
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(expTime);
	    String formattedExpDate = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
	    
		System.out.println("SeatHoldID :"+this+" expTime :"+formattedExpDate);
		this.setExpiryTime(expTime);
	}	
	
	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getseatHoldId() {
		return seatHoldId;
	}

	public void setseatHoldId() {
		Random rand=new Random();
		int randInt = rand.nextInt(10000)+1;
		this.seatHoldId = randInt;
	}

	
	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}	
	
    public String toString() {
    	String listString = "";

    	for (String s : this.getSeats())
    	{
    	    listString += s + " ";
    	}
        return String.format(this.getseatHoldId() + " " + this.getEmailId() +" "+ listString);
    }	

}