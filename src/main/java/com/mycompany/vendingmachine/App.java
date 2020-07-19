package com.mycompany.vendingmachine;

import java.lang.Exception;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Iterator;

class Snack
{
	private String snackName;
	private double price = 0.0;

	public Snack(String snackName, double price) throws Exception {
		if (price <= 0) 
			throw new Exception ("Invalid price " + price);

		this.snackName = snackName;
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public String toString() {
		return "\n\t\tsnackName = " + snackName + " and price = " + price;
	}
}

class Aisle
{
	private String aisleIdentifier;
	private int currCount = 0;
	private Snack snack;

	public Aisle(int depth, String aisleIdentifier) throws Exception
	{
		if (depth < 1)
			throw new Exception ("Invalid Depth " + depth);

		this.currCount = depth;
		this.aisleIdentifier = aisleIdentifier;
	}

	public void setSnack(Snack snack) 
	{
		this.snack = snack;
	}

	public Snack getSnack()
	{
		return this.snack;
	}

	public Snack purchaseSnack(double money) throws Exception
	{
		if (this.snack == null)
			throw new Exception ("Snack is not associated with the aisle with identifier " + this.aisleIdentifier);

		if (this.currCount <= 0)
			throw new Exception ("No snacks left in the aisle " + this.aisleIdentifier);

		if (this.snack.getPrice() > money) 
			throw new Exception ("Snack has a price of " + this.snack.getPrice() + " and money " + money + " is less ");

		this.currCount--;
		VendingMachine.cashBalance += this.snack.getPrice();

		return this.snack;
	}

	public String toString() {
		String returnStr = "\n\tAisle identifier is " + aisleIdentifier + " and current count = " + currCount;
	
		if (this.snack != null)
			returnStr += snack;

		return returnStr;
	}
}

class VendingMachine 
{
	protected static double cashBalance = 0.0;

	private int numOfRows = 1;
	private int numOfColumns = 1;
	private int depth = 1;
	private HashMap aisleMap = new HashMap(); 

	public VendingMachine(int numOfRows, int numOfColumns, int depth) throws Exception
	{
		if (numOfRows < 1 || numOfColumns < 1 || depth < 1)
			throw new Exception ("Invalid number of Rows "  + numOfRows + " or columns " + numOfColumns + " or depth " + depth);

		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
		this.depth = depth;

		char aisleChar = 'A';

		for (int i = 0; i < numOfRows; i++) 
		{
			int columnNumber = 1;

			for (int j = 0; j < numOfColumns; j++) 
			{
				String aisleIdentifier = Character.toString(aisleChar) + Integer.toString(columnNumber);
				try {
					Aisle a1 = new Aisle(depth, aisleIdentifier);
					aisleMap.put(aisleIdentifier, a1);
				} catch (Exception e) {
					System.out.println("Exception " + e);
				}

				columnNumber++;
			}
			aisleChar++;
		}
	}

	public void addSnackToAisle (String aisleIdentifier, String snackName, double price) throws Exception 
	{
		Aisle a1 = (Aisle) aisleMap.get(aisleIdentifier);

		if (a1 == null)
			throw new Exception ("Could not find aisle with identifier " + aisleIdentifier);


		Snack s1 = new Snack(snackName, price);

		a1.setSnack(s1);

	}


	public Snack purchaseSnackFromAisle (String aisleIdentifier, double money) throws Exception 
	{
		Aisle a1 = (Aisle) aisleMap.get(aisleIdentifier);
		
		if (a1 == null)
			throw new Exception ("Could not find aisle with identifier " + aisleIdentifier);

		Snack s1;

		try {
			s1 = a1.purchaseSnack(money);

			// cashBalance += s1.getPrice();
			if (money - s1.getPrice() > 0) {
				System.out.println("Change is " + String.format("%.2f", (money - s1.getPrice())));
			} else {
				System.out.println("There is no change");
			}

		} catch (Exception e) {
			throw e;
		}

		return s1;
	}
	
	public String toString() {
		String returnStr = "Aisle Rows = " + numOfRows + " and columns = " + numOfColumns + "and depth = " + depth;

		SortedSet<String> keys = new TreeSet<>(aisleMap.keySet());

		for (String key : keys) { 
			Aisle a1 = (Aisle) aisleMap.get(key);
			returnStr += a1.toString();
		}

		return returnStr;
	}
}

/**
 * Vending Machine
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		try {
			VendingMachine vm1 = new VendingMachine(4, 3, 5);
			vm1.addSnackToAisle("A3", "Peanuts", 1);
			vm1.addSnackToAisle("B2", "Chips", 2.5);
			vm1.addSnackToAisle("D1", "Pretzels", 0.75);
			vm1.addSnackToAisle("C2", "Almonds", 4.5);
			System.out.println("Vending machine VM1 is " + vm1);

			vm1.purchaseSnackFromAisle("A3", 1.3);
			vm1.purchaseSnackFromAisle("C2", 5.0);
			vm1.purchaseSnackFromAisle("B2", 3.2);

			System.out.println("Vending machine VM1 is " + vm1);

			System.out.println("Vending machine cash balance is " + VendingMachine.cashBalance);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
    }
}
