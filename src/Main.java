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
            Account user = new Account();
            user = user.accountAccess();
            Scanner input = new Scanner(System.in);
            //System.out.println(user);

            //selection menu to: see customers/stores list, create store (sellers), message
            if (user.getRole().equalsIgnoreCase("customer")) {
                //customer actions
                Customer customer = new Customer(user.getUsername(), user.getEmail(), user.getPassword(),
                        user.getRole());
                System.out.println("View Stores List [1] or Search for a Seller [2]?");
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
                            break;
                        } else {
                            System.out.println("Invalid input. Please try again.");
                        }
                    }

                //search for a specific seller to message
                } else if (response == 2) {
                    //todo: method doesn't work
                    customer.searchSeller();

                } else {
                    System.out.println("Invalid input.");
                }

            } else if (user.getRole().equalsIgnoreCase("seller")) {
                //note: looping implemented!
                //seller actions
                System.out.println("View Customers List [1] or Search for a Customer [2]?");
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
                            break;
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
                                break;
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


        } catch (IOException e) {
            e.printStackTrace();
        } catch (CantMessageException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * public class Main {
 *     public static void main(String[] args) {
 *         try {
 *             Account user = new Account();
 *             user = user.accountAccess();
 *             Scanner input = new Scanner(System.in);
 *             //System.out.println(user);
 *
 *             //selection menu to: see customers/stores list, create store (sellers), message
 *             if (user.getRole().equalsIgnoreCase("customer")) {
 *                 //customer actions
 *                 System.out.println("View Stores List [1] or Search for a Seller [2]?");
 *                 int response = input.nextInt();
 *                 input.nextLine();
 *
 *                 //view stores list and choose one of the stores (sellers) to message
 *                 if (response == 1) {
 *                     //todo: print through the stores list when the method is created
 *
 *                 //search for a specific seller to message
 *                 } else if (response == 2) {
 *                     //waiting for if there's going to be a method for this ?
 *                 }
 *
 *             } else if (user.getRole().equalsIgnoreCase("seller")) {
 *                 //seller actions
 *                 System.out.println("View Customers List [1] or Search for a Customer [2]?");
 *                 int response = input.nextInt();
 *                 input.nextLine();
 *
 *                 //view customers list and choose one of the customers to message
 *                 if (response == 1) {
 *                     for (String customer : user.getCustomerList()) {
 *                         System.out.println(customer);
 *                     }
 *
 *                     //looping that will continue to loop unless user selects to cancel messaging [0]
 *                     while (true) {
 *                         //blocking would be implemented here
 *                         System.out.println("\nMessage User [1] or Cancel [0]");
 *                         int newResponse = input.nextInt();
 *                         input.nextLine();
 *                         if (newResponse == 1) {
 *                             System.out.println("Select a user to message: ");
 *                             String userToMessage = input.nextLine();
 *
 *                             Message message = new Message(user, userToMessage);
 *                             message.printMessageHistory();
 *
 *                             //test for sending message
 *                             System.out.println("Enter Message:");
 *                             message.sendMessage(input.nextLine());
 *
 *                         } else if (newResponse == 0) {
 *                             System.out.println("Messaging quit successfully.");
 *                             break;
 *                         } else {
 *                             System.out.println("Invalid input. Please try again.");
 *                         }
 *                     }
 *
 *                 //search for a specific customer to message
 *                 } else if (response == 2) {
 *                     //todo: implement looping
 *                     System.out.println("Please enter a username. Remember that all names are case-sensitive. ");
 *                     String inputUsername = input.nextLine();
 *
 *                     //check if inside the accounts list
 *                     BufferedReader bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
 *                     String filelineRaw = bfr.readLine();
 *                     int counter = 0;
 *
 *                     //if line is found in the file, counter = 1
 *                     while (filelineRaw != null) {
 *                         String[] filelineSep = filelineRaw.split(",");
 *                         String filelineUser = filelineSep[0];
 *                         String filelineUserRole = filelineSep[3];
 *                         if (inputUsername.equals(filelineUser)) {
 *                             //if the roles are different, can message
 *                             if (!filelineUserRole.equalsIgnoreCase(user.getRole())) {
 *                                 counter++;
 *                             //if the roles are same, throw exception
 *                             } else {
 *                                 throw new CantMessageException(user.getRole());
 *                             }
 *                         }
 *                         filelineRaw = bfr.readLine();
 *                     }
 *
 *                     if (counter == 0) {
 *                         System.out.println("User not found.");
 *                     } else {
 *                         //user found, messaging user
 *                         //blocking would be implemented here
 *                         System.out.println("Message user " + inputUsername + "[1] or Cancel [0]");
 *                         int newestResponse = input.nextInt();
 *                         input.nextLine();
 *
 *                         if (newestResponse == 1) {
 *                             Message message = new Message(user, inputUsername);
 *                             message.printMessageHistory();
 *
 *                             //test for sending message
 *                             System.out.println("Enter Message:");
 *                             message.sendMessage(input.nextLine());
 *                         }
 *
 *                     }
 *
 *                 } else {
 *                     System.out.println("Invalid input.");
 *                 }
 *             }
 *
 *
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *         } catch (CantMessageException e) {
 *             e.printStackTrace();
 *         } catch (Exception e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 */
