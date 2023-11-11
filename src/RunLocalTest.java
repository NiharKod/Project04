import java.io.IOException;

public class RunLocalTest {
    public static void main(String[] args) throws IOException {

        Account account = new Account("TestUser1", "Test1@gmail.com", "Test@1", "Seller");
        Account account2 = new Account("TestUser2", "Test2@gmail.com", "Test@2", "Customer");

        Seller seller = new Seller(account);
        System.out.println("Seller Side:");
        System.out.println();
        System.out.println("Created store TestStore1?: " + seller.createStore("TestStore1"));
        System.out.println("Created store TestStore2?: " + seller.createStore("TestStore2"));
        System.out.println();
        System.out.println("Customer List: ");
        seller.viewCustomers();
        System.out.println();
        System.out.println("Found User2?: " + seller.searchCustomer("TestUser2"));
        System.out.println("-----------------------------------");
        System.out.println();

        Customer customer = new Customer(account2);
        System.out.println("Customer Side:");
        System.out.println();
        System.out.println("Stores List: ");
        customer.viewStores();
        System.out.println();
        System.out.println("Found User1?: " + customer.searchSeller("TestUser1"));
        System.out.println();
        System.out.println("Seller of Store, TestStore1: " + customer.storeToSeller("TestStore1"));
    }
}
