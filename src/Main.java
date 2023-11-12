import java.io.IOException;
import java.util.Scanner;

/**
 * notes:
 * figure out how to keep code running even after exception (while loop around the whole try-catch?)
 * blocking has not been implemented yet, need to do that
 */

public class Main {
    private static String CUSTOMER_CHOICES = "View Stores List [1], Search for a Seller [2], View Statistics [3]," +
            " or Cancel [0]?";
    private static String SELLER_CHOICES = "View Customers List [1], Search for a Customer [2], View Statistics [3], " +
            "Create Store [4], or Cancel [0]?";
    private static String INVALID_NUMBER_OPTION = "Invalid Input, please enter valid option!\n";
    private static String SEARCH_USERNAME = "Please enter a username. Remember that all names are case-sensitive.";
    private static String USER_NOT_FOUND = "The username you entered doesn't exist.";
    private static String STORE_NOT_FOUND = "The username you entered doesn't exist.";
    private static String TRY_AGAIN = "Would you like to try again? [Y] or [N]: ";
    private static String MESSAGING_AND_BLOCKING_CHOICES = "Message [1], Block [2], Edit Conversation [3]," +
            " Delete a Message [4], Become Invisible [5], or Cancel [0]";


    public static void main(String[] args) {
        try {
            do {    // Account Loop - Keeps entire program running
                int choice = 0;    // Integer choice user gives to select from options
                String toSearch = null;    // Username of user to send message to
                boolean foundUser = false;    // Determines if user was found and can be messaged

                Scanner input = new Scanner(System.in);
                Account user = new Account();
                user = user.accountAccess();

                // Check if user was created, otherwise ask to quit or loop back to initial account access options
                if (user.getRole() != null) {

                    // All User Actions Loop
                    userActions:
                    do {    // Loops through all actions after account creation, breaking returns to Account Loop

                        // ROLE CHOICES
                        roleChoices:
                        do {    // Loops through seller/customer initial options, breaking moves to User Verification

                            // CUSTOMERS:
                            if (user.getRole().equalsIgnoreCase("customer")) {
                                Customer customer = new Customer(user);

                                // Print Initial Customer Choices
                                do {
                                    System.out.println(CUSTOMER_CHOICES);

                                    // Saves input and checks if it is a valid integer input
                                    String repsonse = input.nextLine();
                                    try {
                                        choice = Integer.parseInt(repsonse);
                                        if (choice > 3 || choice < 0) {
                                            System.out.println(INVALID_NUMBER_OPTION);
                                        } else {
                                            break;
                                        }
                                    } catch (Exception e) {
                                        System.out.println(INVALID_NUMBER_OPTION);
                                    }
                                } while (true);    // Ensures valid choice

                                // Customer choice
                                switch (choice) {
                                    case 1:    // View Stores
                                        for (String store : user.getSellerListInvis()) {
                                            System.out.println(store);
                                        }

                                        // USER VERIFICATION
                                        do {
                                            System.out.println("Please enter store name you would like to " +
                                                    "interact with: ");
                                            String storeSearch = input.nextLine();

                                            // Change storeSearch to its corresponding Seller
                                            toSearch = customer.storeToSeller(storeSearch);

                                            // Check if seller list contains searched seller
                                            foundUser = customer.getSellerList().contains(toSearch);

                                            // Offer loop or quit if user not found, or if user is invisible
                                            if (!foundUser || customer.checkIfCantSee(toSearch)) {
                                                System.out.println(USER_NOT_FOUND);

                                                // Ask user if they'd like to try again? If Y continue, else quit.
                                                System.out.println(TRY_AGAIN);
                                                String again = input.nextLine().toUpperCase();
                                                if (!again.equals("Y")) {
                                                    break;
                                                }
                                            } else if (foundUser) {
                                                break roleChoices;
                                            }
                                        } while (true);    // User existence verifier
                                        break;

                                    case 2:    // Search for Seller
                                        do {
                                            System.out.println(SEARCH_USERNAME);
                                            toSearch = input.nextLine();

                                            // Call the searchSeller method with the entered seller's name
                                            foundUser = customer.searchSeller(toSearch);

                                            // User was found
                                            if (foundUser) {
                                                break roleChoices;
                                            }

                                            // If user isn't found, ask to try again or quit searching
                                            System.out.println(USER_NOT_FOUND);
                                            System.out.println(TRY_AGAIN);
                                            String again = input.nextLine().toUpperCase();

                                            // Ask user if they'd like to try again? If Y continue, else quit.
                                            if (!again.equals("Y")) {
                                                break;
                                            }
                                        } while (true);    // Ensures valid user or cancel
                                        break;

                                    case 3:    // View Statistics
                                        // TODO: IMPLEMENT CUSTOMER SIDE STATISTICS

                                        break;

                                    case 0:    // Cancel
                                        System.out.println("Thank you!");
                                        break userActions;

                                }


                                // SELLERS:
                            } else if (user.getRole().equalsIgnoreCase("seller")) {
                                Seller seller = new Seller(user);

                                // Print Initial Seller Choices
                                do {
                                    System.out.println(SELLER_CHOICES);

                                    // Saves input and checks if it is a valid integer input
                                    String repsonse = input.nextLine();
                                    try {
                                        choice = Integer.parseInt(repsonse);
                                        if (choice > 4 || choice < 0) {
                                            System.out.println(INVALID_NUMBER_OPTION);
                                        } else {
                                            break;
                                        }
                                    } catch (Exception e) {
                                        System.out.println(INVALID_NUMBER_OPTION);
                                    }
                                } while (true);    // Ensures valid choice

                                // Seller choice
                                switch (choice) {
                                    case 1:    // View Customers and selection
                                        for (String customer : user.getCustomerListInvis()) {
                                            System.out.println(customer);
                                        }

                                        // USER VERIFICATION
                                        do {
                                            System.out.println("Please enter customer username you would like to " +
                                                    "interact with: ");
                                            toSearch = input.nextLine();

                                            // Check if customer list contains searched customer
                                            foundUser = seller.getCustomerList().contains(toSearch);

                                            // Offer loop or quit if user not found, or if user is invisible
                                            if (!foundUser || seller.checkIfCantSee(toSearch)) {
                                                System.out.println(USER_NOT_FOUND);

                                                // Ask user if they'd like to try again? If Y continue, else quit.
                                                System.out.println(TRY_AGAIN);
                                                String again = input.nextLine().toUpperCase();
                                                if (!again.equals("Y")) {
                                                    break;
                                                }
                                            } else if (foundUser) {
                                                break roleChoices;
                                            }
                                        } while (true);     // User existence verifier
                                        break;

                                    case 2:    // Search for Customer
                                        do {
                                            System.out.println(SEARCH_USERNAME);
                                            toSearch = input.nextLine();

                                            // Call the searchCustomer method with the entered customer's name
                                            foundUser = seller.searchCustomer(toSearch);

                                            // User was found
                                            if (foundUser) {
                                                break roleChoices;
                                            }

                                            // If user isn't found, ask to try again or quit searching
                                            System.out.println(USER_NOT_FOUND);
                                            System.out.println(TRY_AGAIN);
                                            String again = input.nextLine().toUpperCase();

                                            // Ask user if they'd like to try again? If Y continue, else quit.
                                            if (!again.equals("Y")) {
                                                break;
                                            }
                                        } while (true);    // Ensures valid user or cancel
                                        break;

                                    case 3:    // View Statistics
                                        // TODO: IMPLEMENT STATISTICS HERE
                                        break;

                                    case 4:    // Create Store
                                        do {
                                            System.out.println("Enter new store name: ");
                                            String storeName = input.nextLine();

                                            boolean createdStore = seller.createStore(storeName);

                                            if (createdStore) {
                                                System.out.println("Store, " + storeName + ", created!");
                                                break;
                                            } else {
                                                System.out.println("Store name already taken, try a new name!");

                                                // Ask user if they'd like to try again? If Y continue, else quit.
                                                System.out.println(TRY_AGAIN);
                                                String again = input.nextLine().toUpperCase();
                                                if (!again.equals("Y")) {
                                                    break;
                                                }
                                            }
                                        } while (true);
                                        break;
                                    case 0:    // Cancel
                                        System.out.println("Thank you!");
                                        break userActions;
                                }
                            }
                        } while (true);    // Seller-Customer Option Loop

                        // MESSAGING, BLOCKING, AND INVISIBILITY
                        messagingChoices:
                        while (true) {    // Loops through and asks about messaging, blocking, and invisibilty options
                            // Messaging, Blocking, and Invisibility Options
                            do {
                                System.out.println(MESSAGING_AND_BLOCKING_CHOICES);

                                // Saves input and checks if it is a valid integer input
                                String repsonse = input.nextLine();
                                try {
                                    choice = Integer.parseInt(repsonse);
                                    if (choice > 5 || choice < 0) {
                                        System.out.println(INVALID_NUMBER_OPTION);
                                    } else {
                                        break;
                                    }
                                } catch (Exception e) {
                                    System.out.println(INVALID_NUMBER_OPTION);
                                }
                            } while (true);

                            // Messaging, Blocking, and Invisibility Choices
                            switch (choice) {    // TODO: CHECK ALL OF THIS NONSENSE
                                case 1:    // Message
                                    if (user.checkIfBlocked(toSearch)) {
                                        System.out.println("You have been blocked by this user and cannot message!");
                                    } else {
                                        Message message = new Message(user, toSearch);
                                        message.printMessageHistory();

                                        System.out.println("Enter Message:");
                                        message.sendMessage(input.nextLine());
                                    }
                                    break;

                                case 2:    // Block
                                    System.out.println("Select a user to block: ");
                                    String userToBlock = input.nextLine();

                                    user.writeBlockedByList(userToBlock);
                                    System.out.println("You have blocked user " + userToBlock + "!");
                                    break;

                                case 3:    // Edit
                                    Message message = new Message(user, toSearch);
                                    message.printMessageHistoryWithIndeces();

                                    System.out.println("Select a line to edit: ");
                                    int lineToEdit = input.nextInt();
                                    input.nextLine();

                                    System.out.println("Enter the new message: ");
                                    String newMessage = input.nextLine();

                                    message.editMessage(lineToEdit, newMessage);
                                    break;

                                case 4:    // Delete
                                    message = new Message(user, toSearch);
                                    message.printMessageHistoryWithIndeces();

                                    System.out.println("Select a line to delete: ");
                                    int lineToDelete = input.nextInt();
                                    input.nextLine();

                                    message.deleteMessage(lineToDelete);
                                    break;

                                case 5:    // Become Invisible
                                    user.writeCantSeeList(toSearch);
                                    System.out.println("You have become invisible to " + toSearch + "!");
                                    break;

                                case 0:    // Cancel
                                    System.out.println("Messaging quit successfully.");
                                    break messagingChoices;

                            }

                        } // Messaging Loop
                    } while (true);
                }

                // Close program entirely?
                System.out.println("Would you like to close the program? [Y] or [N]: ");
                String again = input.nextLine().toUpperCase();

                // Ask user if they'd like to close the program? If N continue, else quit.
                if (!again.equals("N")) {
                    System.out.println("Closing.");
                    break;
                }

            } while (true);    // Account Loop
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CantMessageException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}