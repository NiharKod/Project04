public class Seller extends Account {
    public Seller(String username, String email, String password, String role) {
        super(username, email, password, role);
    }

    public Seller(Account account) {

    }

    public void createStore() {

    }

    public void viewCustomers() {

    }

    public boolean searchCustomer() {
        return true;
    }
}