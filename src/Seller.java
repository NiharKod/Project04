import java.io.*;
import java.util.ArrayList;

public class Seller extends Account {

    ArrayList<String> stores = new ArrayList<>();
    PrintWriter pw;
    BufferedReader br;
    File f;

    public Seller(String username, String email, String password, String role) throws IOException {
        super(username, email, password, role);
        //grab store information from file
        f = new File(super.getUsername() + ".txt");
        pw = new PrintWriter(new BufferedWriter(new FileWriter(super.getUsername() + ".txt")));
        br = new BufferedReader(new FileReader(super.getUsername() + ".txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            stores.add(line);
        }
    }

    public Seller(Account account) throws IOException {
        super(account);
        f = new File(super.getUsername() + ".txt");
        pw = new PrintWriter(new BufferedWriter(new FileWriter(super.getUsername() + ".txt")));
        br = new BufferedReader(new FileReader(super.getUsername() + ".txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            stores.add(line);
        }
    }

    public boolean createStore(String sName) throws IOException {
        boolean storeFound = checkStores(sName);

        if (!storeFound) {    // If checkStore does not find store in list
            //add to list of stores
            stores.add(sName);
            //delete the file;
            f.delete();
            //remake the file;
            pw = new PrintWriter(new BufferedWriter(new FileWriter(super.getUsername() + ".txt")));
            //append the file with each line being the store in the list;
            for (String s : stores) {
                pw.println(s);
                pw.flush();
            }
            pw.close();

            PrintWriter pwStores = new PrintWriter(new FileWriter("stores.txt"));
            pwStores.println(sName);
            pwStores.close();
            return true;
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
        BufferedReader bfrStores = new BufferedReader(new FileReader(new File("stores.txt")));
        String line = bfrStores.readLine();

        while (line != null) {
            if (line.equals(storeName)) {
                return true;
            }
            line = bfrStores.readLine();
        }
        return false;
    }


}