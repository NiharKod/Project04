import java.awt.print.PrinterAbortException;
import java.io.*;

public class Message {
    private Account from;
    private Account to;
    PrintWriter pwTo;
    PrintWriter pwFrom;
    BufferedReader br;

    public Message(Account from, String to) throws CantMessageException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
        String line;
        //finding the user account and assigning it to the to.
        while ((line = br.readLine()) != null) {
            if (line.split(",")[0].equals(to) && !line.split(",")[3].equals(from.getRole())) {
                this.to.setEmail(line.split(",")[0]);
                this.to.setUsername(line.split(",")[1]);
                this.to.setPassword(line.split(",")[2]);
                this.to.setRole(line.split(",")[3]);
            } else {
                throw new CantMessageException(from.getRole());
            }
        }
        //creating the file for messaging ot finding it if it is there already.
        this.from = from;
        //create the to file if its not there already.

        pwTo = new PrintWriter(new FileOutputStream(from.getUsername() + "-"
                + this.to.getUsername() + ".txt", true));
        //create the from file if it is not there already;

        pwFrom = new PrintWriter(new FileOutputStream(this.to.getUsername() + "-"
                + from.getUsername() + ".txt", true));
    }

    public void sendMessage(String message) {
        pwFrom.printf("(%s)%s: %s", to.getRole(), to.getUsername(), message);
        pwFrom.flush();
        pwTo.printf("(%s)%s: %s", to.getRole(), to.getUsername(), message);
        pwTo.flush();

    }

    public void printMessageHistory() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
        String line;
        //finding the user account and assigning it to the to.
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("-");
    }


}
