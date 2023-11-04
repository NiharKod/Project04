import java.io.*;

public class Message {
    private Account from;
    private Account to;
    PrintWriter pw;
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
        PrintWriter pw = new PrintWriter(new FileOutputStream(from.getUsername() + "-"
                + this.to.getUsername() + ".txt", true));
    }

    public void sendMessage(String message) {
        pw.printf("(%s)%s: %s", to.getRole(), to.getUsername(), message);
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
