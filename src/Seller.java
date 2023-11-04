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

    public Seller(Account account) {

    }

    public void createStore(String sName) throws IOException {
        //add to list of stores
        stores.add(sName);
        //delete the file;
        f.delete();
        //remake the file;
        pw = new PrintWriter(new BufferedWriter(new FileWriter(super.getUsername() + ".txt")));
        //append the file with each line being the store in the list;
        for (String s : stores) {
            pw.println(s);
        }
    }

    public void viewCustomers() {

    }

    public boolean searchCustomer() {
        return true;
    }
}