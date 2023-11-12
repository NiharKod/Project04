import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String [] args )  {
        try {
            List<String> messages = loadMessages();
            displayMessagesByCustomer(messages);
            displayMostCommonWords(messages);
        }catch( Exception e ){
          e.printStackTrace();
        }

    }

    private static List<String> loadMessages() throws IOException {
        List<String> messages = new ArrayList<>();
        //FileStore a = Files.getFileStore(Path.of("txt"));
        String fileName = "niharkod-Carlos.txt";
       // FileReader fileReader = new FileReader(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                messages.add(line);
            }
        }
        System.out.println(messages);
        return messages;
    }

    private static void displayMessagesByCustomer(List<String> messages) {
        Map<String, Integer> messageCountMap = new HashMap<>();

        for (String message : messages) {
            String customer = extractCustomerFromMessage(message);
            messageCountMap.put(customer, messageCountMap.getOrDefault(customer, 0) + 1);
        }

        System.out.println("Number of messages sent by each customer:");
        for (Map.Entry<String, Integer> entry : messageCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " messages");
        }
    }

    private static String extractCustomerFromMessage(String message) {
        // Assuming the format is (Role)Username: Message
        int count = message.trim().indexOf(':');
        String concatMessages = message.substring(count+1).trim();
     //   return concatMessages.split("\\)")[1].split(":")[0];
        return concatMessages.split("\\)")[0];
    }

    private static void displayMostCommonWords(List<String> messages) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String message : messages) {
            String removeNames = extractCustomerFromMessage(message);
            String[] words = removeNames.split("\\s+");
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("\nMost common words in overall messages:");
        wordCountMap.entrySet().stream().max(Map.Entry.comparingByValue()).stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " occurrences"));
    }
}
