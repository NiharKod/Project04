import java.io.*;
import java.util.ArrayList;

public class Seller extends Account {

    ArrayList<String> stores = new ArrayList<>();

    public static ArrayList<String> allStores = new ArrayList<>();
    PrintWriter pw;
    BufferedReader br;

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

    public boolean createStore(String sName) throws IOException {
        if (!checkStores(sName)) {
            String newLine = super.getUsername();
            for (String s : stores) {
                newLine += ("," + s);
            }
            newLine+= sName;
            pw = new PrintWriter(new BufferedWriter(new FileWriter("storemanager.txt")));
            br = new BufferedReader(new FileReader("storemanager.txt"));

        } else {
            return false;
        }
    }

    public void viewCustomers() {

    }

    public boolean searchCustomer() {
        return true;
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