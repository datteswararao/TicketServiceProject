package com.walmart.tickets;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

public class SeatReserveMain implements TicketService {
	final static int rows = 7, cols = 4;
	static char[][] seats = new char[rows][cols];
	static Scanner scan = new Scanner(System.in);

	static Hashtable<Integer, SeatHold> seatHoldHash = new Hashtable<Integer, SeatHold>();
	static Hashtable<Integer, String> seatReserved = new Hashtable<Integer, String>();

	public SeatReserveMain() {

		// this will initialize the seats with row numbers and alphabet letters.
		char seatLetter = 'A';
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++)
				seats[i][j] = seatLetter++;
			seatLetter = 'A';
		}
	}

	public static void main(String[] args) {

		SeatReserveMain q = new SeatReserveMain();

		SeatHold seatHoldObj = null;

		displayMenu();
		int menuChoice;
		menuChoice = scan.nextInt();
		scan.nextLine();
		
		while (menuChoice <= 3) {
			switch (menuChoice) {
			case 1:
				System.out.println("How many seats you want to hold?");
				int seatHoldSize = scan.nextInt();
				scan.nextLine();
				if (seatHoldSize <= q.numSeatsAvailable()) {
					System.out.println("Please give us your emailID?");
					String emailID = scan.next();
					scan.nextLine();
					seatHoldObj = q.findAndHoldSeats(seatHoldSize, emailID);
					
					Timer timer = new Timer();
					timer.schedule(new CleanUpTask(seatHoldHash), 0, 1000);
					
				}
				q.printSeats(seats);
				displayMenu();

				menuChoice = scan.nextInt();
				break;

			case 2:
				System.out.println("Enter your SeatID:");
				int seatHoldID = scan.nextInt();
				scan.nextLine();
				System.out.println("Enter your EmailID:");
				String emailID = scan.next();
				scan.nextLine();

				boolean canConfirm = false;
				canConfirm = q.CheckSeatID(seatHoldID);

				String confirmationID = null;
				if (canConfirm) {
					System.out.println("You want to reserve tickets ? (Y/N) :");
					String canReserve = scan.next();
					if (canReserve.toUpperCase().equalsIgnoreCase("Y")) {
						confirmationID = q.reserveSeats(seatHoldID, emailID);
						System.out.println("Your seats were confirmed. Here is the confimation ID :" + confirmationID);
					} else
						q.cancelHoldSeats(seatHoldID);
				}

				q.printSeats(seats);

				displayMenu();
				menuChoice = scan.nextInt();
				break;

			case 3:
				System.out.println("Here are all available seating (H - HELD, R - RESERVED)!");
				q.printSeats(seats);
				displayMenu();
				menuChoice = scan.nextInt();
				break;
			case 4:
				System.out.println("Have a wonderful day!");
				System.exit(0);
			}
		}

		scan.close();
	}

	public static void displayMenu() {
		System.out.println("Choose an Option: ");
		System.out.println("1. Hold Seats!");
		System.out.println("2. Confirm Seat with SeatID!");
		System.out.println("3. Display All Available Seats!");
		System.out.println("4. Quit!");
	}

	public boolean CheckSeatID(int seatHoldID) {

		SeatHold seatHoldObj = null;

		seatHoldObj = seatHoldHash.get(seatHoldID);

		if (null != seatHoldObj) {
			System.out.println(seatHoldObj.getseatHoldId());

			List<String> seatsHeld = seatHoldObj.getSeats();
			if (seatsHeld.size() != 0) {
				Iterator<String> itr = seatsHeld.iterator();
				System.out.println("Here are the ticket, you held with the reservation :" + seatHoldID);
				while (itr.hasNext()) {
					System.out.print(itr.next() + " ");
				}
				return true;
			}

			// System.out.println(" "+seatHoldObj.getSeats())
		}else {
			System.out.println("SeatHoldID " + seatHoldID+" doesn't exists anymore!!!");
		}

		return false;
	}

	public void printSeats(char[][] seats) {
		for (int i = 0; i < rows; i++) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < cols; j++)
				System.out.print(seats[i][j] + " ");
			System.out.println();
		}
	}

	public static boolean checkSeatAvailability(char[][] saSeatArray, int row, int col) {
		boolean available = false;

		if ((saSeatArray[row - 1][col - 1] == 'H') || (saSeatArray[row - 1][col - 1] == 'R')) {
			available = false;
		} else
			available = true;
		return available;
	}

	public static boolean isAllFull(char[][] saSeatArray) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols;  col++) {
				if (saSeatArray[row][col] == 'H' || saSeatArray[row][col] == 'R')
					return true;
				else
					return false;
			}
		}
		return true;
	}

	@Override
	public int numSeatsAvailable() {
		int count = 0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (seats[row][col] != 'H' || seats[row][col] != 'R') {
					count++;
				}	
			}
		}
		return count;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {

		List<String> holdSeats = new ArrayList<String>();

		System.out.println("Pick your seats to Hold: ");
		// Scanner scanHold = new Scanner(System.in);
		for (int seat = 0; seat < numSeats; seat++) {
			String seatHoldStr = null;

			boolean seatTakenAlready = false; // seat taken already by someone
												// else!!!

			do {
				seatHoldStr = scan.next();
				scan.nextLine();
				int row = Integer.parseInt(seatHoldStr.substring(0, 1));
				char col = seatHoldStr.charAt(1);
				int col_int = -1;
				if (Character.isLowerCase(col))
					col_int = (int) col - (int) 'a' + 1;
				if (Character.isUpperCase(col))
					col_int = (int) col - (int) 'A' + 1;

				if (checkSeatAvailability(seats, row, col_int)) {
					seats[row - 1][col_int - 1] = 'H'; // temp hold
					holdSeats.add(seatHoldStr);
					System.out.println(" Seat " + row + col + " is held for you!!! ");
					seatTakenAlready = false;
				} else {
					System.out.println(
							"Sorry! The Seat " + row + col + " is NOT available. Please look up for another seat.");
					seatTakenAlready = true;
				}
			} while (seatTakenAlready);

		}

		// scanHold.close();
		SeatHold seatHoldObj = new SeatHold(customerEmail, holdSeats);

		System.out.println("Use this Seat Held ID :" + seatHoldObj.getseatHoldId() + " for your final reservation!!!");

		seatHoldHash.put(seatHoldObj.getseatHoldId(), seatHoldObj);

		return seatHoldObj;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {

		SeatHold seatHoldObj = null;

		seatHoldObj = seatHoldHash.get(seatHoldId);

		List<String> seatsHeld = seatHoldObj.getSeats();

		Iterator<String> itr = seatsHeld.iterator();
		while (itr.hasNext()) {
			String selectedSeat = (String) itr.next();
			int row = Integer.parseInt(selectedSeat.substring(0, 1));
			char col = selectedSeat.charAt(1);
			int col_int = (int) col - (int) 'A' + 1;

			seats[row - 1][col_int - 1] = 'R';
		}

		Random rand = new Random();
		int randInt = rand.nextInt(1000000) + 1;

		seatReserved.put(randInt, customerEmail);

		return randInt + "";
	}

	public void cancelHoldSeats(int seatHoldID) {
		SeatHold seatHoldObj = null;

		seatHoldObj = seatHoldHash.get(seatHoldID);

		List<String> seatsHeld = seatHoldObj.getSeats();

		Iterator itr = seatsHeld.iterator();
		while (itr.hasNext()) {
			String selectedSeat = (String) itr.next();
			int row = Integer.parseInt(selectedSeat.substring(0, 1));
			char col = selectedSeat.charAt(1);
			int col_int = (int) col - (int) 'A' + 1;

			seats[row - 1][col_int - 1] = (char) (col_int + 64); // REASSIGN BACK THE
																	// ASCII
																	// CHAR TO
																	// THE
																	// SEATING
		}
		seatHoldHash.remove(seatHoldID);
	}

		
		
	}
