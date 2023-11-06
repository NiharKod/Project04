import java.io.BufferedReader;
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

    // Method to load user accounts and their roles from "accounts.txt" file
    public void loadUserAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    String user = userData[0].trim();
                    String role = userData[3].trim();
                    usernames.add(user); // Add the username to the list
                    roles.add(role); // Add the role to the list
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user accounts from the file");
        }
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
    public void searchSeller() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the username of the seller you want to search:");
        String input = scanner.nextLine();

        int index = usernames.indexOf(input);
        if (index != -1 && roles.get(index).equalsIgnoreCase("Seller")) {
            assignedSeller = input;
            System.out.println("Assigned seller: " + assignedSeller);
        } else {
            System.out.println("No seller found with that username.");
        }
    }

    public String getAssignedSeller() {
        return assignedSeller;
    }
}



