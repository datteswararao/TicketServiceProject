package com.walmart.tickets;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Set;
import java.util.TimerTask;

public class CleanUpTask extends TimerTask {

	Hashtable<Integer, SeatHold> seatHoldHash = null;

	CleanUpTask(Hashtable<Integer, SeatHold> seatHoldHashVar) {
		this.seatHoldHash = seatHoldHashVar;
	}
	
	@Override
	public void run() {
		
		SeatHold seatHoldObj = null;
		
		if(!seatHoldHash.isEmpty()) {
	        Set<Integer> keys = seatHoldHash.keySet();
	        for(Integer key: keys){
	        	seatHoldObj = seatHoldHash.get(key);
	        	if(Calendar.getInstance().getTimeInMillis() >= seatHoldObj.getExpiryTime()) {
	        		System.out.println("seatHoldHash HoldID Before remove due to inactivity :"+seatHoldHash.toString());
	        		seatHoldHash.remove(key);
	        		
	        	}
	        }
		}
		
	}
	


}
