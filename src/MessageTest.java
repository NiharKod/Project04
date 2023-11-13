import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;

public class MessageTest {

    private Account sender;
    private Account receiver;
    private Message message;

    @Before
    public void setUp() {
        // Create sender and receiver accounts for testing
        sender = new Account("niharkod", "nkodkani@purdue.edu", "fancybook3!2", "seller");
        receiver = new Account("jdoe", "jdoe@gmail.com", "Password123!", "customer");
        // Create a Message instance
        try {
            message = new Message(sender, receiver.getUsername());
        } catch (CantMessageException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMessage() {
        try {
            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Send a message
            message.sendMessage("Test message");

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the sent message is in the printed output
            assertTrue(printedMessage.contains("Test message"));

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing sendMessage: " + e.getMessage());
        }
    }

    @Test
    public void testEditMessage() {
        try {
            // Send a message
            message.sendMessage("Original message");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Edit the message
            message.editMessage(1, "Edited message");

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the edited message is in the printed output
            assertTrue(printedMessage.contains("Edited message"));

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing editMessage: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteMessage() {
        try {
            // Send a message
            message.sendMessage("Test message");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Delete the message
            message.deleteMessage(1);

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the message is deleted
            assertFalse(printedMessage.contains("Test message"));

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing deleteMessage: " + e.getMessage());
        }
    }

    @Test
    public void testPrintMessageHistory() {
        try {
            // Send messages
            message.sendMessage("Message 1");
            message.sendMessage("Message 2");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Print message history
            message.printMessageHistory();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the history is correctly printed
            assertTrue(printedMessage.contains("Message 1"));
            assertTrue(printedMessage.contains("Message 2"));

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing printMessageHistory: " + e.getMessage());
        }
    }

    @Test
    public void testDisplayMostCommonWords() {
        try {
            // Send messages with common words
            message.sendMessage("apple banana apple");
            message.sendMessage("banana cherry apple");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Display most common words
            message.displayMostCommonWords();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the most common words are correctly displayed
            assertTrue(printedMessage.contains("apple: 3 occurrences"));
            assertFalse(printedMessage.contains("banana: 2 occurrences"));
            assertFalse(printedMessage.contains("cherry:"));

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing displayMostCommonWords: " + e.getMessage());
        }
    }

    @Test
    public void testDisplayLeastCommonWords() {
        try {
            // Send messages with common and uncommon words
            message.sendMessage("apple banana apple");
            message.sendMessage("banana cherry apple");
            message.sendMessage("cherry kiwi");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Display least common words
            message.displayLeastCommonWords();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the least common words are correctly displayed
            assertTrue(printedMessage.contains("kiwi: 1 occurrences"));
            assertFalse(printedMessage.contains("banana:"));

            // Clean up
            System.setOut(System.out);
        } catch (IOException e) {
            fail("Exception occurred while testing displayLeastCommonWords: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        // Clean up by deleting the created message history files
        String filenameFrom = sender.getUsername() + "-" + receiver.getUsername() + ".txt";
        String filenameTo = receiver.getUsername() + "-" + sender.getUsername() + ".txt";

        File fileFrom = new File(filenameFrom);
        File fileTo = new File(filenameTo);

        if (fileFrom.exists()) {
            fileFrom.delete();
        }

        if (fileTo.exists()) {
            fileTo.delete();
        }
    }
}
