import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Statistics class
 *
 * <p>Purdue University -- CS18000 -- Fall 2023 -- Project 04 -- </p>
 *
 * @author Nihar Kodkani, Nangba Konyak, Isabelle Lee, Sandesh Reddy
 * @version November 12th, 2023
 */
public class Statistics {
    // Method to display number of messages sent by each customer
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

    // Method to extract customer username from a message
    private static String extractCustomerFromMessage(String message) {
        // Assuming the format is (Role)Username: Message
        return message.split("\\)")[1].split(":")[0];
    }

    // Method to display most common words in overall messages
    private static void displayMostCommonWords(List<String> messages) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String message : messages) {
            String[] words = message.split("\\s+");
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("\nMost common words in overall messages:");
        wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " occurrences"));
    }

    // Method to display stores by the number of messages received
    private static void displayStoresByMessagesReceived(List<String> messages) {
        Map<String, Integer> storeReceivedCountMap = new HashMap<>();

        for (String message : messages) {
            String store = extractStoreFromMessage(message);
            storeReceivedCountMap.put(store, storeReceivedCountMap.getOrDefault(store, 0) + 1);
        }

        System.out.println("\nStores by the number of messages received:");
        storeReceivedCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " messages"));
    }

    // Method to extract store name from a message
    private static String extractStoreFromMessage(String message) {
        // Assuming the format is (Role)Username: Message
        return message.split("\\)")[1].split(":")[0];
    }

    // Method to display stores by the number of messages sent by the customer
    private static void displayStoresByMessagesSent(List<String> messages, String customerUsername) {
        Map<String, Integer> storeSentCountMap = new HashMap<>();

        for (String message : messages) {
            String store = extractStoreFromMessage(message);
            if (storeSentCountMap.containsKey(store)) {
                storeSentCountMap.put(store, storeSentCountMap.get(store) + 1);
            } else {
                storeSentCountMap.put(store, 1);
            }
        }

        System.out.println("\nStores by the number of messages sent by " + customerUsername + ":");
        storeSentCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " messages"));
    }

}