import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Message {
    private Account from;
    private Account to;

    public Message(Account from, Account to) throws CantMessageException, IOException {
        if (!from.getRole().equals(to.getRole())) {
            this.from = from;
            this.to = to;
            PrintWriter pw = new PrintWriter(new FileOutputStream(from.getUsername() + "-"
                    + to.getUsername() + ".txt", true));
        } else {
            throw new CantMessageException(from.getRole());
        }
    }






}
