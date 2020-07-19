package com.mycompany.vendingmachine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.Rule;
import org.junit.BeforeClass;

import java.lang.Exception;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	static VendingMachine vm1;

	@BeforeClass
	public static void oneTimeSetup() throws Exception {
		try {
			vm1 = new VendingMachine(3, 4, 5);
			vm1.addSnackToAisle("A3", "Peanuts", 1);
			vm1.addSnackToAisle("B2", "Chips", 2.5);
			vm1.addSnackToAisle("B4", "Pretzels", 0.75);
			vm1.addSnackToAisle("C2", "Almonds", 4.5);
		} catch (Exception e) {
			System.err.println("Caught an exception during setup " + e);
		}
	}
     
	@Test
	public void testAddingToWrongLocation() {
		try {
			vm1.addSnackToAisle("E3", "junk", 3.5);
			fail("Expected to throw an exception for wrong aisle location, but was not thrown");
		} catch (Exception e) {
			System.err.println("Caught exception correctly for wrong location " + e.getMessage());
		}
	}


	@Test
	public void testPurchasingWithLessAmount() {
		try {
			vm1.purchaseSnackFromAisle("A3", 0.3);
			fail("Expected to throw an exception for less amount, but was not thrown");
		} catch (Exception e) {
			System.err.println("Caught exception correctly for less amount " + e.getMessage());
		}
	}

	@Test
	public void testPurchaseFromNoSnack() {
		try {
			vm1.purchaseSnackFromAisle("C3", 1.0);
			fail("Expected to throw an exception for missing snack, but was not thrown");
		} catch (Exception e) {
			System.err.println("Caught exception correctly for no snack " + e.getMessage());
		}
	}
}
