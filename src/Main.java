import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * notes:
 * figure out how to keep code running even after exception (while loop around the whole try-catch?)
 */

public class Main {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            Account user = new Account();
            user = user.accountAccess();

            //System.out.println(user);

            /**
             * below is for customer actions
             */
            if (user.getRole().equalsIgnoreCase("customer")) {
                //customer actions
                Customer customer = new Customer(user.getUsername(), user.getEmail(), user.getPassword(),
                        user.getRole());

                outer: while (true) {
                    System.out.println("View Stores List [1], Search for a Seller [2], or Cancel [0]?");
                    int response = input.nextInt();
                    input.nextLine();

                    //view stores list and choose one of the stores (sellers) to message
                    if (response == 1) {
                        customer.viewStores();

                        //looping continues forever until user chooses cancel [0]
                        while (true) {
                            //blocking would be implemented here as 'Block User [2]'
                            System.out.println("\nMessage Store [1] or Cancel [0]");
                            int newResponse = input.nextInt();
                            input.nextLine();

                            if (newResponse == 1) {
                                System.out.println("Select a store to message: ");
                                String storeToMesssage = input.nextLine();

                                //todo here since store doesn't match with seller automatically

                                Message message = new Message(customer, storeToMesssage);
                                message.printMessageHistory();

                                System.out.println("Enter Message:");
                                message.sendMessage(input.nextLine());
                            } else if (newResponse == 0) {
                                System.out.println("Messaging quit successfully.");
                                break outer;
                            } else {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        //search for a specific seller to message
                        //search for a specific seller to message
                    } else if (response == 2) {
                        System.out.println("Enter the seller's username to search: ");
                        String sellerToSearch = input.nextLine();

                        // Call the searchSeller method with the entered seller's name
                        boolean foundSeller = customer.searchSeller(sellerToSearch);

                        if (foundSeller) {
                            System.out.println("Seller found. You can now message the seller.");
                            // Implement messaging logic with the found seller
                        } else {
                            System.out.println("Seller not found.");
                        }
                    }
                    else if (response == 0) {
                        System.out.println("System quit successfully.");
                        break;
                    } else {
                        System.out.println("Invalid input. Please try again.\n");
                    }
                }

                /**
                 * below is for seller actions
                 */
            } else if (user.getRole().equalsIgnoreCase("seller")) {
                //note: looping implemented!
                //seller actions
                outer: while (true) {
                    System.out.println("View Customers List [1], Search for a Customer [2], or Cancel [0]?");
                    int response = input.nextInt();
                    input.nextLine();

                    //view customers list and choose one of the customers to message
                    if (response == 1) {
                        System.out.println("List of all customers:");
                        for (String customer : user.getCustomerList()) {
                            System.out.println(customer);
                        }

                        //looping that will continue to loop unless user selects to cancel messaging [0]
                        while (true) {
                            //blocking would be implemented here as 'or Block User [2]'
                            System.out.println("\nMessage User [1] or Cancel [0]");
                            int newResponse = input.nextInt();
                            input.nextLine();
                            if (newResponse == 1) {
                                System.out.println("Select a user to message: ");
                                String userToMessage = input.nextLine();

                                Message message = new Message(user, userToMessage);
                                message.printMessageHistory();

                                System.out.println("Enter Message:");
                                message.sendMessage(input.nextLine());

                            } else if (newResponse == 0) {
                                System.out.println("Messaging quit successfully.");
                                break outer;
                            } else {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }

                        //search for a specific customer to message
                    } else if (response == 2) {
                        System.out.println("Please enter a username. Remember that all names are case-sensitive. ");
                        String inputUsername = input.nextLine();

                        //check if inside the accounts list
                        Seller seller = new Seller(user);
                        boolean search = seller.searchCustomer(inputUsername);

                        //looping will implement until user types [0] to cancel
                        while (true) {
                            //customer username is found, can message
                            if (search) {
                                //blocking would be implemented here as 'or block customer [2]'
                                System.out.println("Message Customer " + inputUsername + " [1] or Cancel [0]");
                                int newResponse = input.nextInt();
                                input.nextLine();

                                if (newResponse == 1) {
                                    Message message = new Message(user, inputUsername);
                                    message.printMessageHistory();

                                    System.out.println("Enter Message:");
                                    message.sendMessage(input.nextLine());

                                } else if (newResponse == 0) {
                                    System.out.println("Messaging quit successfully.");
                                    break outer;
                                } else {
                                    System.out.println("Invalid Input. Please try again.");
                                }

                                //customer username not found, can't message
                            } else {
                                throw new CantMessageException(user.getRole());
                            }
                        }

                    } else {
                        System.out.println("Invalid input.");
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (CantMessageException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}