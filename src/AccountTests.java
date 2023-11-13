import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;

/**
 * AccountTests class
 *
 * <p>Purdue University -- CS18000 -- Fall 2023 -- Project 04 -- </p>
 *
 * @author Nihar Kodkani, Nangba Konyak, Isabelle Lee, Sandesh Reddy
 * @version November 12th, 2023
 */
public class AccountTests {
    public static class TestCase {
        private Account account;

        @Before
        public void setUp() {
            account = new Account("jdoe", "test@example.com", "testPassword", "Customer");
        }

        @Test
        public void testGetters() {
            System.out.println("Running Test Cases...");
            assertEquals("jdoe", account.getUsername());
            assertEquals("test@example.com", account.getEmail());
            assertEquals("testPassword", account.getPassword());
            assertEquals("Customer", account.getRole());
        }

        @Test
        public void testSetters() {
            account.setUsername("newUser");
            account.setEmail("new@example.com");
            account.setPassword("newPassword");
            account.setRole("Seller");

            assertEquals("newUser", account.getUsername());
            assertEquals("new@example.com", account.getEmail());
            assertEquals("newPassword", account.getPassword());
            assertEquals("Seller", account.getRole());
        }

        @Test
        public void testCheckFiles() throws IOException {
            // Create a temporary test file with account information
            File testFile = new File("test_accounts.txt");
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("testUser,test@example.com,testPassword,Customer");
            writer.close();

            // Check if the test account is found in the test file
            assertTrue(account.checkFiles(1, "test_accounts.txt"));

            // Clean up the test file
            testFile.delete();
        }

        @Test
        public void testGetCustomerList() throws IOException {
            // Create a temporary test file with customer accounts
            File testFile = new File("test_accounts.txt");
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("customer1,customer1@example.com,password,Customer");
            writer.println("seller1,seller1@example.com,password,Seller");
            writer.println("customer2,customer2@example.com,password,Customer");
            writer.close();

            // Get the customer list and check its contents
            ArrayList<String> customers = account.getCustomerList("test_accounts.txt");
            assertTrue(customers.contains("customer1"));
            assertTrue(customers.contains("customer2"));
            assertFalse(customers.contains("seller1"));

            // Clean up the test file
            testFile.delete();
        }

        @Test
        public void testGetSellerList() throws IOException {
            // Create temporary test file
            File testFile = new File("test_accounts.txt");
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("customer1,customer1@example.com,password,Customer");
            writer.println("seller1,seller1@example.com,password,Seller");
            writer.println("customer2,customer2@example.com,password,Customer");
            writer.close();

            // Get the customer list and check its contents
            ArrayList<String> sellers = account.getSellerList("test_accounts.txt");
            assertFalse(sellers.contains("customer1"));
            assertFalse(sellers.contains("customer2"));
            assertTrue(sellers.contains("seller1"));

            // Clean up the test file
            testFile.delete();
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AccountTests.TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}
