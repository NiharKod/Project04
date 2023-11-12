import java.awt.print.PrinterAbortException;
import java.io.*;
import java.util.ArrayList;

public class Message {
    private Account from;
    private Account to = new Account();
    PrintWriter pwTo;
    PrintWriter pwFrom;
    BufferedReader br;

    public Message(Account from, String to) throws CantMessageException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
        String line;
        //finding the user account and assigning it to the to.
        while ((line = br.readLine()) != null) {
            if (line.split(",")[0].equals(to) && !line.split(",")[3].equals(from.getRole())) {
                this.to.setEmail(line.split(",")[1]);
                this.to.setUsername(line.split(",")[0]);
                this.to.setPassword(line.split(",")[2]);
                this.to.setRole(line.split(",")[3]);
            }
        }
        if (this.to.getUsername() == null) {
            throw new CantMessageException(from.getRole());
        }
        //creating the file for messaging ot finding it if it is there already.
        this.from = from;
        //create the to file if its not there already.

        pwTo = new PrintWriter(new FileOutputStream(this.to.getUsername() + "-"
                + from.getUsername() + ".txt", true));

        pwFrom = new PrintWriter(new FileOutputStream(from.getUsername() + "-"
                + this.to.getUsername() + ".txt", true));
        //create the from file if it is not there already;

    }

    public void sendMessage(String message) throws IOException {
        pwFrom.printf("(%s)%s: %s\n", from.getRole(), from.getUsername(), message);
        pwFrom.flush();
        pwTo.printf("(%s)%s: %s\n", from.getRole(), from.getUsername(), message);
        pwTo.flush();
        printMessageHistory();
    }

    public void printMessageHistory() throws IOException {
        BufferedReader brFrom = new BufferedReader(new FileReader(from.getUsername() + "-"
                + this.to.getUsername() + ".txt"));
        String line;
        //finding the user account and assigning it to the to.
        while ((line = brFrom.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("-");
    }

    public void printMessageHistoryWithIndeces() throws IOException {
        BufferedReader brFrom = new BufferedReader(new FileReader(from.getUsername() + "-"
                + this.to.getUsername() + ".txt"));
        String line;
        int i = 1;
        //finding the user account and assigning it to the to.
        while ((line = brFrom.readLine()) != null) {
            System.out.printf("[%d] %s\n", i, line);
            i++;
        }
        System.out.println("-");
    }

    public void editMessage(int lineIndex, String updatedMessage) throws IOException {
        String originalMessage = "";
        //read in the from message.
        BufferedReader brFrom = new BufferedReader(new FileReader(from.getUsername() + "-"
                + this.to.getUsername() + ".txt"));

        //read every line into an array list.
        ArrayList<String> messages = new ArrayList<>();
        String line;
        while ((line = brFrom.readLine()) != null) {
            messages.add(line);
        }
        //store original message for later
        originalMessage = messages.get(lineIndex - 1);
        //update the message
        messages.set(lineIndex - 1, String.format("(%s)%s: %s", from.getRole(), from.getUsername(), updatedMessage));

        //delete original file
        File f = new File(from.getUsername() + "-"
                + this.to.getUsername() + ".txt");

        f.delete();
        //rewrite contents with deleted line.
        pwFrom = new PrintWriter(new FileOutputStream(from.getUsername() + "-"
                + this.to.getUsername() + ".txt", true));

        for (String m : messages) {
            pwFrom.println(m);
            pwFrom.flush();
        }

        //read the to file
        BufferedReader brTo = new BufferedReader(new FileReader(this.to.getUsername() + "-"
                + from.getUsername() + ".txt"));

        //read every line into an array list.

        messages.clear();
        line = "";

        while ((line = brTo.readLine()) != null) {
            messages.add(line);
        }

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).equals(originalMessage)) {
                messages.set(i, String.format("(%s)%s: %s", from.getRole(), from.getUsername(), updatedMessage));
                break;
            }
        }
        //delete original file
        f = new File(this.to.getUsername() + "-"
                + from.getUsername() + ".txt");
        f.delete();
        //rewrite contents with deleted line.
        pwTo = new PrintWriter(new FileOutputStream(this.to.getUsername() + "-"
                + from.getUsername() + ".txt", true));
        for (String m : messages) {
            pwTo.println(m);
            pwTo.flush();
        }
        //print out history.
        printMessageHistory();

        pwTo.close();
        pwFrom.close();
        brFrom.close();
        brTo.close();
    }

    public void deleteMessage(int i) throws IOException {
        BufferedReader brFrom = new BufferedReader(new FileReader(from.getUsername() + "-"
                + this.to.getUsername() + ".txt"));
        //read every line into an array list. delete line.
        ArrayList<String> messages = new ArrayList<>();
        String line;
        while ((line = brFrom.readLine()) != null) {
            messages.add(line);
        }
        //remove message
        messages.remove(i - 1);
        //delete original file
        File f = new File(from.getUsername() + "-"
                + this.to.getUsername() + ".txt");
        f.delete();
        //rewrite contents with deleted line.
        pwFrom = new PrintWriter(new FileOutputStream(from.getUsername() + "-"
                + this.to.getUsername() + ".txt", true));
        for (String m : messages) {
            pwFrom.println(m);
            pwFrom.flush();
        }
        //print out history.
        printMessageHistory();
        pwFrom.close();
        brFrom.close();
    }
}