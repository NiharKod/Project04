import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Account {
    private String role;
    private String assignedSeller;
    private List<String> usernames; // List to store usernames
    private List<String> roles; // List to store roles
    private List<String> storeNames; // List to store store names

    public Customer(String username, String email, String password, String role) {
        super(username, email, password, role);
        this.usernames = new ArrayList<>(); // Initialize the usernames list
        this.roles = new ArrayList<>(); // Initialize the roles list
        this.assignedSeller = null; // Initialize assignedSeller to null
        this.storeNames = new ArrayList<>(); // Initialize the storeNames list
    }

    public Customer(Account account) {
        super(account);
        this.usernames = new ArrayList<>(); // Initialize the usernames list
        this.roles = new ArrayList<>(); // Initialize the roles list
        this.assignedSeller = null; // Initialize assignedSeller to null
        this.storeNames = new ArrayList<>(); // Initialize the storeNames list
    }

    // Method to view all stores from "storemanager.txt" file
    public void viewStores() {
        try (BufferedReader br = new BufferedReader(new FileReader("storemanager.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length > 1) {
                    for (int i = 1; i < userData.length; i++) {
                        storeNames.add(userData[i].trim()); // Add store names to the list
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading store data from the file");
        }

        if (storeNames.isEmpty()) {
            System.out.println("No stores found.");
        } else {
            // Display all stores
            System.out.println("List of all stores:");
            for (String storeName : storeNames) {
                System.out.println(storeName);
            }
        }
    }

    // Method to search for a seller and assign if found
    public boolean searchSeller(String sellerName) throws IOException {
        if (getSellerList().contains(sellerName)) {
            assignedSeller = sellerName; // Assign the seller to the customer
            return true;
        } else {
            return false;
        }
    }

    // Method to find seller name from storename
    public String storeToSeller(String storeName) {
        try (BufferedReader br = new BufferedReader(new FileReader("storemanager.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length > 1) {
                    for (int i = 1; i < userData.length; i++) {
                        if (userData[i].trim().equals(storeName)) {
                            // Return the username (assuming the first element is the username)
                            return userData[0].trim();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading store data from the file");
        }

        // Return an empty string if the store name is not found
        return "";
    }
}



