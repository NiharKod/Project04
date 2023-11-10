import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {
    private String username;
    private String email;
    private String password;
    private String role;

    ArrayList<String> customers = new ArrayList<>();
    ArrayList<String> stores = new ArrayList<>();
    ArrayList<String> sellers = new ArrayList<>();


    // Constructors
    public Account(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account(Account account) {
        this.username = account.username;
        this.email = account.email;
        this.password = account.password;
        this.role = account.role;
    }

    public Account() {
        this.username = null;
        this.email = null;
        this.password = null;
        this.role = null;
    }


    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }


    // Other Methods
    public Account accountAccess() throws IOException {
        Scanner scan = new Scanner(System.in);
        int accountChoice;

        do {    // Gets user's choice to log in to a pre-existing account or create a new account
            System.out.println("Log-In [1] or Create New Account [2]?");
            String repsonse = scan.nextLine();

            try {
                accountChoice = Integer.parseInt(repsonse);
                if (accountChoice != 1 && accountChoice != 2) {
                    System.out.println("Invalid Input, please enter 1 or 2\n");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input, please enter 1 or 2\n");
            }

        } while (true);


        boolean accountFound;
        int roleChoice;
        Account account = new Account();
        if (accountChoice == 1) {    // Login to Pre-Existing Account
            System.out.println("LOG-IN:");

            do {
                do {    // Validate Email
                    System.out.println("Please Enter your Email:");
                    setEmail(scan.nextLine());

                    if (getEmail().contains("@") && !getEmail().contains(",") && !getEmail().contains(" ")) {
                        break;
                    } else {
                        System.out.println("Please enter a valid email!\n");
                    }
                } while (true);

                do {    // Validate Password
                    System.out.println("Please Enter your Password:");
                    setPassword(scan.nextLine());

                    if (!getPassword().contains(",") && !getPassword().contains(" ")) {
                        break;
                    } else {
                        System.out.println("Please enter a valid password!");
                    }
                } while (true);


                // Check if given email and password exists in accounts.txt
                accountFound = checkFiles(accountChoice);
                if (!accountFound) {    // Account not found
                    System.out.println("Incorrect credentials!");
                    System.out.println("Would you like to try again? [Y] or [N]:");
                    String again = scan.nextLine().toUpperCase();

                    // Ask user if they'd like to try logging in again? If Y continue, else quit and return null.
                    if (!again.equals("Y")) {
                        System.out.println("Thank you!");
                        return account;
                    }

                } else {    // Account found
                    System.out.println("Welcome back, " + getRole() + " " + getUsername() + "!\n");
                }

            } while (!accountFound);
        } else if (accountChoice == 2) {    // Create New Account
            System.out.println("CREATE ACCOUNT:");

            do {
                do {    // Validate Username
                    System.out.println("Please Create a Username:");
                    setUsername(scan.nextLine());

                    if (!getUsername().contains(",") && !getUsername().contains(" ")) {
                        break;
                    } else {
                        System.out.println("Please enter a valid username!");
                    }
                } while (true);

                do {    // Validate Email
                    System.out.println("Please Enter your Email:");
                    setEmail(scan.nextLine());

                    if (getEmail().contains("@") && !getEmail().contains(",") && !getEmail().contains(" ")
                            && !getEmail().startsWith("@") && !getEmail().endsWith("@")) {
                        break;
                    } else {
                        System.out.println("Please enter a valid email!");
                    }
                } while (true);

                do {    // Validate Password
                    System.out.println("Please Enter your Password (Must be 6 characters and" +
                            " have 1 special character [!,@,#,$,%]):");
                    setPassword(scan.nextLine());

                    if ((!getPassword().contains(",") && !getPassword().contains(" ") && (getPassword().length() >= 6)) &&
                            (getPassword().contains("!") || getPassword().contains("@") || getPassword().contains("#")
                                    || getPassword().contains("$") || getPassword().contains("%"))) {
                        break;
                    } else {
                        System.out.println("Please enter a valid password!");
                    }
                } while (true);

                // Seller/Customer role selector code
                do {
                    System.out.println("Are you a Seller [1] or Customer [2]?:");
                    int option = scan.nextInt();
                    scan.nextLine();

                    setRole((option == 1) ? "Seller" : (option == 2) ? "Customer" : "");
                    try {
                        roleChoice = option;
                        if (roleChoice != 1 && roleChoice != 2) {
                            System.out.println("Invalid Input, please enter 1 or 2\n");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid Input, please enter 1 or 2\n");
                    }
                } while (true);

                accountFound = checkFiles(accountChoice);
                if (accountFound) {    // Account already exists
                    System.out.println("Email or username already in use!");
                    System.out.println("Would you like to try again? [Y] or [N]:");
                    String again = scan.nextLine().toUpperCase();

                    // Ask user if they'd like to try creating account again? If Y continue, else quit and return null.
                    if (!again.equals("Y")) {
                        System.out.println("Thank you!");
                        return account;
                    }
                } else {    // Account doesn't already exist
                    System.out.println("Account created for " + getUsername() + "!\n");
                }
            } while (accountFound);

            // Add new account info to accounts.txt
            FileOutputStream fos = new FileOutputStream("accounts.txt", true);
            PrintWriter pw = new PrintWriter(fos);
            pw.printf("%s,%s,%s,%s\n", getUsername(), getEmail(), getPassword(), getRole());
            pw.flush();
            pw.close();
        }

        account = new Account(getUsername(), getEmail(), getPassword(), getRole());

        if (getRole().equals("Seller") && accountChoice == 2) {    // If new account is a seller
            Seller seller = new Seller(account);

            do {
                System.out.println("Please create a store: ");
                String storeName = scan.nextLine();

                boolean createdStore = seller.createStore(storeName);

                if (createdStore) {
                    System.out.println("Store, " + storeName + ", created!");
                    break;
                } else {
                    System.out.println("Store name already taken, try a new name!");
                }

            } while (true);
        }

        return account;
    }


    // Checks if provided account information is found in accounts.txt
    public boolean checkFiles(int accountChoice) throws IOException {
        File f = new File("accounts.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        String line = bfr.readLine();

        while (line != null) {
            String[] accountInfo = line.split(",");
            if (accountChoice == 1) {    // Log In: Find email and password
                if (accountInfo[1].equals(getEmail()) && accountInfo[2].equals(getPassword())) {
                    setUsername(accountInfo[0]);
                    setRole(accountInfo[3]);
                    return true;
                }
            } else if (accountChoice == 2) {    // Create Account: See if email is available
                if (accountInfo[1].equals(getEmail()) || accountInfo[0].equals(getUsername())) {
                    return true;
                }
            }
            line = bfr.readLine();
        }

        bfr.close();
        return false;
    }

    // Creates an arrayList of all customers
    public ArrayList<String> getCustomerList() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.split(",")[3].equals("Customer")) {
                customers.add(line.split(",")[0]);
            }
        }
        br.close();
        return customers;
    }

    // Creates an arrayList of all sellers
    public ArrayList<String> getSellerList() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.split(",")[3].equals("Seller")) {
                sellers.add(line.split(",")[0]);
            }
        }
        br.close();
        return sellers;
    }

    // Creates an arrayList of stores

    public String toString() {
        return String.format("(Account)<Username=%s,Email=%s,Password=%s,Role=%s>", username, email, password, role);
    }
}