import java.awt.print.PrinterAbortException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Message class
 *
 * <p>Purdue University -- CS18000 -- Fall 2023 -- Project 04 -- </p>
 *
 * @author Nihar Kodkani, Nangba Konyak, Isabelle Lee, Sandesh Reddy
 * @version November 12th, 2023
 */
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
        //read in the "from" message.
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

    // Below are all the methods for statistics
// Statistics have been written in messages to make it easy for testcasing it
    public void displayMostCommonWords() throws IOException {
        List<String> messages = new ArrayList<>();
        BufferedReader brFrom = new BufferedReader(new FileReader(from.getUsername() + "-"
                + this.to.getUsername() + ".txt"));
        String line;

        while ((line = brFrom.readLine()) != null) {
            messages.add(line);
        }
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String message : messages) {
            String removeNames = extractCustomerFromMessage(message);
            String[] words = removeNames.split("\\s+");
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        final int[] maxOccurrences = {0};
        final List<String> mostCommonWords = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            int occurrences = entry.getValue();
            if (occurrences > maxOccurrences[0]) {
                maxOccurrences[0] = occurrences;
                mostCommonWords.clear();
                mostCommonWords.add(entry.getKey());
            } else if (occurrences == maxOccurrences[0]) {
                mostCommonWords.add(entry.getKey());
            }
        }

        System.out.println("\nMost common words in overall messages with " + maxOccurrences[0] + " occurrences:");
        mostCommonWords.forEach(word -> System.out.println(word + ": " + maxOccurrences[0] + " occurrences"));
    }


    public void displayLeastCommonWords() throws IOException {
        List<String> messages = new ArrayList<>();
        BufferedReader brFrom = new BufferedReader(new FileReader(from.getUsername() + "-"
                + this.to.getUsername() + ".txt"));
        String line;

        while ((line = brFrom.readLine()) != null) {
            messages.add(line);
        }
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String message : messages) {
            String removeNames = extractCustomerFromMessage(message);
            String[] words = removeNames.split("\\s+");
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        final int[] minOccurrences = {Integer.MAX_VALUE};
        final List<String> leastCommonWords = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            int occurrences = entry.getValue();
            if (occurrences < minOccurrences[0]) {
                minOccurrences[0] = occurrences;
                leastCommonWords.clear();
                leastCommonWords.add(entry.getKey());
            } else if (occurrences == minOccurrences[0]) {
                leastCommonWords.add(entry.getKey());
            }
        }

        if (minOccurrences[0] == Integer.MAX_VALUE) {
            minOccurrences[0] = 0;
        }

        System.out.println("\nLeast common words in overall messages with " + minOccurrences[0] + " occurrences:");
        leastCommonWords.forEach(word -> System.out.println(word + ": " + minOccurrences[0] + " occurrences"));
    }


    private static String extractCustomerFromMessage(String message) {
        // Assuming the format is (Role)Username: Message
        int count = message.trim().indexOf(':');
        String concatMessages = message.substring(count + 1).trim();
        //   return concatMessages.split("\\)")[1].split(":")[0];
        return concatMessages.split("\\)")[0];
    }
}