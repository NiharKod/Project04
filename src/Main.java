import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Account user = new Account();
            user = user.accountAccess();
            //System.out.println(user);


            //output the info depending on the user







        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
