import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Account class
 *
 * <p>Purdue University -- CS18000 -- Fall 2023 -- Project 04 -- </p>
 *
 * @author Nihar Kodkani, Nangba Konyak, Isabelle Lee, Sandesh Reddy
 * @version November 12th, 2023
 */
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

            loginLoop:
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
                    do {
                        System.out.println("Would you like to try again? [Y] or [N]:");
                        String again = scan.nextLine().toUpperCase();

                        // Ask user if they'd like to try logging in again? If Y continue, else quit and return null.
                        if (again.equals("Y")) {
                            System.out.println();
                            break;
                        } else if (again.equals("N")) {
                            System.out.println();
                            break loginLoop;
                        } else if (!again.equals("N") || !again.equals("Y")) {
                            System.out.println("Invalid Input.");
                        }

                    } while (true);    // Try Again?
                } else {    // Account found
                    System.out.println("Welcome back, " + getRole() + " " + getUsername() + "!\n");
                }

            } while (!accountFound);
        } else if (accountChoice == 2) {    // Create New Account
            System.out.println("CREATE ACCOUNT:");

            newAccountLoop:
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
                            " have 1 special character [!,@,#,$,%,?]):");
                    setPassword(scan.nextLine());

                    if ((!getPassword().contains(",") && !getPassword().contains(" ") && (getPassword().length() >= 6))
                            && (getPassword().contains("!") || getPassword().contains("@") ||
                            getPassword().contains("#") || getPassword().contains("$") || getPassword().contains("%"))
                            || getPassword().contains("?")) {
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
                    do {
                        System.out.println("Would you like to try again? [Y] or [N]:");
                        String again = scan.nextLine().toUpperCase();

                        // Ask user if they'd like to try creating account again? If Y continue, else quit and return null.
                        if (again.equals("Y")) {
                            System.out.println();
                            break;
                        } else if (again.equals("N")) {
                            System.out.println();
                            break newAccountLoop;
                        } else if (!again.equals("N") || !again.equals("Y")) {
                            System.out.println("Invalid Input.");
                        }
                    } while (true);
                } else {    // Account doesn't already exist
                    System.out.println("Account created for " + getUsername() + "!\n");
                }
            } while (accountFound);

            // Create user's cantSee and blockedBy txt files
            writeBlockedByList(getUsername());
            writeCantSeeList(getUsername());

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
    public boolean checkFiles(int accountChoice, String filename) throws IOException {
        File f = new File(filename);
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

    public boolean checkFiles(int accountChoice) throws IOException {
        return checkFiles(accountChoice, "accounts.txt");
    }

    // Creates an arrayList of all customers
    public ArrayList<String> getCustomerList(String filename) throws IOException {
        customers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.split(",")[3].equals("Customer")) {
                customers.add(line.split(",")[0]);
            }
        }
        br.close();
        return customers;
    }

    public ArrayList<String> getCustomerList() throws IOException {
        return getCustomerList("accounts.txt");
    }

    //calls getCustomerList and checks for invisibility
    public ArrayList<String> getCustomerListInvis() throws IOException {
        ArrayList<String> customerList = this.getCustomerList();

        File f = new File(this.username + "CantSeeList.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> cantSeeList = new ArrayList<>();

        String line = bfr.readLine();
        while (line != null) {
            cantSeeList.add(line);
            line = bfr.readLine();
        }

        if (cantSeeList != null) {
            for (int i = 0; i < cantSeeList.size(); i++) {
                customerList.remove(cantSeeList.get(i));
            }
        }
        return customerList;
    }

    // Creates an arrayList of all sellers
    public ArrayList<String> getSellerList(String filename) throws IOException {
        sellers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.split(",")[3].equals("Seller")) {
                sellers.add(line.split(",")[0]);
            }
        }
        br.close();
        return sellers;
    }

    public ArrayList<String> getSellerList() throws IOException {
        return getSellerList("Accounts.txt");
    }

    public ArrayList<String> getSellerListInvis() throws IOException {
        ArrayList<String> sellerList = this.getSellerList();

        File f = new File(this.username + "CantSeeList.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> cantSeeList = new ArrayList<>();

        String line = bfr.readLine();
        while (line != null) {
            cantSeeList.add(line);
            line = bfr.readLine();
        }

        //get the seller list, accounting for invisibility
        if (cantSeeList != null) {
            for (int i = 0; i < cantSeeList.size(); i++) {
                sellerList.remove(cantSeeList.get(i));
            }
        }

        BufferedReader storeManagerBFR = new BufferedReader(new FileReader("storemanager.txt"));
        ArrayList<String> storeList = new ArrayList<>();

        String lineStoreManager = storeManagerBFR.readLine();
        while (lineStoreManager != null) {
            String[] lineSep = lineStoreManager.split(",", 2);

            for (int i = 0; i < sellerList.size(); i++) {
                if (sellerList.get(i).equals(lineSep[0])) {
                    String[] lineSep2 = lineSep[1].split(",");
                    for (String store : lineSep2) {
                        storeList.add(store);
                    }
                }
            }

            lineStoreManager = storeManagerBFR.readLine();
        }

        return storeList;
    }

    public String toString() {
        return String.format("(Account)<Username=%s,Email=%s,Password=%s,Role=%s>", username, email, password, role);
    }

    //blocking section
    //if user1 is on user2's blocked-by list, user2 cannot send a message to user1
    public void writeBlockedByList(String username) throws FileNotFoundException {
        File f = new File(username + "BlockedByList.txt");
        FileOutputStream fos = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(fos);

        pw.println(this.username);
        pw.flush();
    }

    //method checks if user has a blocked-by list, and if the person they want to message is on the list
    //if person they want to message is on their blocked-by list, return true, thus they are blocked and
    //won't be allowed to message the person
    public boolean checkIfBlocked(String username) throws IOException {
        File f = new File(this.username + "BlockedByList.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> blockedByList = new ArrayList<>();

        String line = bfr.readLine();
        while (line != null) {
            blockedByList.add(line);
            line = bfr.readLine();
        }

        return f.exists() && blockedByList.contains(username);
    }

    //if user1 is on user2's cant-see list, user2 will not be able to search for user1
    public void writeCantSeeList(String username) throws FileNotFoundException {
        File f = new File(username + "CantSeeList.txt");
        FileOutputStream fos = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(fos);

        pw.println(this.username);
        pw.flush();
    }

    //if returns true, means the user is not allowed to see the other person's usernmae
    public boolean checkIfCantSee(String username) throws IOException {
        File f = new File(this.username + "CantSeeList.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> cantSeeList = new ArrayList<>();

        String line = bfr.readLine();
        while (line != null) {
            cantSeeList.add(line);
            line = bfr.readLine();
        }

        return f.exists() && cantSeeList.contains(username);
    }
}