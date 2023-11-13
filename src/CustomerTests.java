import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerTests {

    // Create a test customer account
    Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("jdoe", "jdoe@gmail.com", "Password123!", "Customer");
    }

    @Test
    public void testSearchSeller() {
        try {
            // Search for an existing seller
            boolean result = customer.searchSeller("niharkod");
            assertTrue(result);
            assertEquals("niharkod", customer.getAssignedSeller());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testStoreToSeller() {
        try {
            // Find a seller by store name
            String seller = customer.storeToSeller("ssbooks");
            assertEquals("sandesh", seller);

            // Try to find a non-existing store name
            seller = customer.storeToSeller("nonexistent");
            assertEquals("", seller);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Helper method to read from the existing store manager file
    private String readStoreManagerFile() throws IOException {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("storemanager.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        }
        return data.toString();
    }
}
