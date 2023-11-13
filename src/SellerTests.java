import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;

public class SellerTests {
    public static class TestCase {
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
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SellerTests.TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}
