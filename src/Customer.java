public class Customer extends Account {
    private String role;

    public Customer(String username, String email, String password, String role) {
        super(username, email, password, role);
    }

    public Customer(Account account) {

    }

    public void viewStores() {

    }

    public boolean searchSeller() {
        return false;
    }
}