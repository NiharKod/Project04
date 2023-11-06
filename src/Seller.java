import java.io.*;
import java.util.ArrayList;

public class Seller extends Account {

    ArrayList<String> stores = new ArrayList<>();

    public static ArrayList<String> allStores = new ArrayList<>();
    PrintWriter pw;
    BufferedReader br;

    // Constructors
    public Seller(String username, String email, String password, String role) throws IOException {
        super(username, email, password, role);
        //grab store information from file
        pw = new PrintWriter(new BufferedWriter(new FileWriter("storemanager.txt")));
        br = new BufferedReader(new FileReader("storemanager.txt"));

        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains(super.getUsername())) {
                for (String s : line.split(",")) {
                    stores.add(s);
                }
                sellers.remove(0);
                break;
            }
        }
    }

    public Seller(Account account) throws IOException {
        super(account);
        pw = new PrintWriter(new BufferedWriter(new FileWriter("storemanager.txt")));
        br = new BufferedReader(new FileReader("storemanager.txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains(super.getUsername())) {
                for (String s : line.split(",")) {
                    stores.add(s);
                }
                sellers.remove(0);
                break;
            }
        }
    }

    // Create store
    public boolean createStore(String storeName) throws IOException {
        if (!checkStores(storeName)) {
            File f = new File("storemanager.txt");
            ArrayList<ArrayList<String>> storeManager = new ArrayList<>();

            // Read through storemanager.txt to convert information to a 2D arrayList called storeManager
            br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                String[] row = line.split(",");
                ArrayList<String> sellerInfo = new ArrayList<>();

                for (int i = 0; i < row.length; i++) {
                    sellerInfo.add(row[i]);
                }

                storeManager.add(sellerInfo);
                line = br.readLine();
            }
            br.close();

            // Find user in storeManager and add storeName to their information
            for (int i = 0; i < storeManager.size(); i++) {
                if (storeManager.get(i).get(0).equals(getUsername())) {
                    storeManager.get(i).add(storeName);
                }
            }

            // Write storeManager to new storemanager.txt
            pw = new PrintWriter(new BufferedWriter(new FileWriter("storemanager.txt")));
            for (int i = 0; i < storeManager.size(); i++) {
                String row = getUsername();
                for (int k = 1; k < storeManager.get(i).size(); k++) {
                    row += "," + storeManager.get(i).get(k);
                }
                pw.println(row);
            }
            return true;
        } else {
            return false;
        }
    }

    public void viewCustomers() throws IOException {
        File f = new File("accounts.txt");
        br = new BufferedReader(new FileReader(f));

        String line = br.readLine();
        while (line != null) {
            String[] row = line.split(",");
            if (row[3].equals("Customer")) {
                System.out.println(row[0]);
            }
        }
    }

    public boolean searchCustomer(String customerName) throws IOException {
        return getCustomerList().contains(customerName);
    }

    public boolean checkStores(String storeName) throws IOException {
        return allStores.contains(storeName);
    }

    public ArrayList<String> getStores() throws IOException {
        br = new BufferedReader(new FileReader("storemanager.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            for (int i = 1; i < line.split(",").length; i++) {
                allStores.add(line.split(",")[i]);
            }
        }
        return allStores;
    }


}