import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
                //todo
            } else if (user.getRole().equalsIgnoreCase("seller")) {
                //seller actions
                System.out.println("View Customers List [1] or Search for a Customer [2]?");
                int response = input.nextInt();
                input.nextLine();

                //view customers list and choose one of the customers to message
                if (response == 1) {
                    //todo
                    for (String customer : user.getCustomerList()) {
                        System.out.println(customer);
                    }

                    System.out.println("\nMessage User [1] or Cancel [0]");
                    int newResponse = input.nextInt();
                    input.nextLine();
                    if (newResponse == 1) {
                        System.out.println("Select a user to message: ");
                        String userToMessage = input.nextLine();

                        Message message = new Message(user, userToMessage);
                        message.printMessageHistory();
                    }

                //search for a specific customer to message
                } else if (response == 2) {
                    //todo/fix
                    System.out.println("Please enter a username. Remember that all names are case-sensitive. ");
                    String inputUsername = input.nextLine();

                    //check if inside the accounts list
                    BufferedReader bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
                    String[] line = bfr.readLine().split(",");
                    String fileUsername = line[0];
                    int counter = 0;


                    while (fileUsername != null) {
                        if (inputUsername.equals(fileUsername)) {
                            counter++;
                        }
                        line = bfr.readLine().split(",");
                        fileUsername = line[0];
                    }

                    if (counter == 0) {
                        System.out.println("User not found.");
                    } else {
                        System.out.println("Would you like to message user " + fileUsername + "?");
                    }

                } else {
                    System.out.println();
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
