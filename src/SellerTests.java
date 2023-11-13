import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class SellerTests {
    private Seller seller;

    @Before
    public void setUp() {
        try {
            seller = new Seller("seller1", "seller1@example.com", "password", "Seller");
        } catch (IOException e) {
            fail("Exception occurred while setting up the test: " + e.getMessage());
        }
    }

    @Test
    public void testViewCustomers() {
        try {
            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            seller.viewCustomers();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Check if the displayed customers match the expected customers based on the data in accounts.txt
            assertTrue(printedMessage.contains("jdoe"));  // jdoe is a customer
            assertFalse(printedMessage.contains("niharkod"));  // niharkod is not a customer

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing viewCustomers: " + e.getMessage());
        }
    }

    @Test
    public void testSearchCustomer() {
        try {
            // Test if searchCustomer can find an existing customer
            assertTrue(seller.searchCustomer("jdoe"));

            // Test if searchCustomer can't find a non-existing customer
            assertFalse(seller.searchCustomer("niharkod"));
        } catch (IOException e) {
            fail("Exception occurred while testing searchCustomer: " + e.getMessage());
        }
    }

    @Test
    public void testGetStores() {
        try {
            ArrayList<String> stores = seller.getStores();

            // Check if the stores match the expected stores based on the data in storemanager.txt
            assertTrue(stores.contains("Microcenter"));
            assertTrue(stores.contains("ssbooks"));

            // Clean up (This part may depend on your specific implementation)
        } catch (IOException e) {
            fail("Exception occurred while testing getStores: " + e.getMessage());
        }
    }

}
